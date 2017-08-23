/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yinyayun.netcarry.core.config.NetCarryConfig;
import org.yinyayun.netcarry.core.config.ProxyFactoryA.ProxyStruct;
import org.yinyayun.netcarry.core.dao.PageMetas;
import org.yinyayun.netcarry.core.parser.FetchParser;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * PageFetchA.java 页面的抓取逻辑
 * 
 * @author yinyayun
 */
public class PageFetch<T> implements Closeable {
    public final Logger logger = LoggerFactory.getLogger(PageFetch.class);
    // 负责待抓取页面入队列
    private ExecutorService executorService;
    // 待收抓取url队列,理论上在磁盘上实现该逻辑会更好
    private LinkedBlockingQueue<PageMetas> tobeCarrayURLQueue;
    /**
     * 已经抓取的URL
     */
    private Set<String> fetchedUrls = new HashSet<String>();
    private ThreadPoolExecutor executor;
    private NextPageParserA[] nextPageParsers;
    private FetchParser<T> parser;
    private NetCarryConfig config;

    public PageFetch(FetchParser<T> parser, NextPageParserA[] nextPageParsers, NetCarryConfig config) {
        this.config = config;
        this.parser = parser;
        this.nextPageParsers = nextPageParsers;
        this.tobeCarrayURLQueue = new LinkedBlockingQueue<PageMetas>(config.getTobeCarryQueueSize());
        this.executor = new ThreadPoolExecutor(config.getFetchThreadNumber(), config.getFetchThreadNumber() * 2, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
        this.executorService = new ThreadPoolExecutor(10, 20, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000), new RejectedExecutionHandler() {

                    @Override
                    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                        new Thread(r).start();
                    }
                });
        for (int i = 0; i < config.getFetchThreadNumber(); i++) {
            executor.submit(() -> {
                fetch();
            });
        }
    }

    public PageFetch(FetchParser<T> parser, NetCarryConfig config) {
        this(parser, null, config);
    }

    /**
     * 开始执行抓取
     * 
     * @param urls
     */
    public void startFetch(List<String> urls) {
        for (String url : urls) {
            addURL(new PageMetas(url, url));
        }
    }

    /**
     * 添加至带抓取队列
     * 
     * @param url
     * @throws InterruptedException
     */
    private void addURL(PageMetas paMetas) {
        executorService.execute(() -> {
            try {
                if (!fetchedUrls.contains(paMetas.getCurrentUrl())) {
                    logger.info("添加至待抓取队列:{}", paMetas.getCurrentUrl());
                    tobeCarrayURLQueue.put(paMetas);
                    fetchedUrls.add(paMetas.getCurrentUrl());
                }
            }
            catch (Exception e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        });
    }

    /**
     * 从待抓取队列中获取URL
     * 
     * @return
     */
    private PageMetas takeURL() {
        try {
            sleep(config.getSleepTime());
            PageMetas page = tobeCarrayURLQueue.take();
            logger.info("从待抓取队列获取:{},队列剩余:{}", page.getCurrentUrl(), tobeCarrayURLQueue.size());
            return page;
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * @param preRule
     * @param commonRule
     */
    private void fetch() {
        // 防止重复抓取
        while (true) {
            // TODO allen 因生产和消费在一个线程，并且生产的URL要多余消费的URL，所以会导致阻塞队列满了之后的阻塞
            PageMetas pageMetas = takeURL();
            String url = pageMetas.getCurrentUrl();
            Connection conn = createConnection(url);
            for (int i = 0; i < config.getRetryTimes(); i++) {
                try {
                    Document document = conn.get();
                    // Document document = Jsoup.parse(new URL(url), 3000);
                    if (parser.needParser(url))
                        parser.fetchPaser(pageMetas, document);
                    // 解析下一页
                    if (nextPageParsers == null) {
                        continue;
                    }
                    for (NextPageParserA nextPageParser : nextPageParsers) {
                        if (nextPageParser.needParserThisPage(url)) {
                            List<PageMetas> nextPages = nextPageParser.nextPage(pageMetas, document);
                            if (nextPages != null && nextPages.size() > 0) {
                                nextPages.forEach(x -> addURL(x));
                            }
                        }
                    }
                    // 成功后则退出
                    break;
                }
                catch (Exception e) {
                    if (i == (config.getRetryTimes() - 1)) {
                        logger.error(e.getMessage(), e);
                        // 重新加入队列
                        addURL(pageMetas);
                    }
                    else {
                        sleep(3000);
                    }
                }
            }
        }
    }

    /**
     * 创建连接
     * 
     * @param url
     * @return
     */
    private Connection createConnection(String url) {
        // Connection conn = Jsoup.connect(url);
        Connection conn = HttpConnection.connect(url);
        //
        conn.ignoreContentType(config.isIgnoreContentType());
        // agent设置
        String agent = config.getAgent();
        if (agent != null && agent.length() > 0) {
            conn.userAgent(agent);
        }
        ProxyStruct proxyStruct = config.getProxy();
        if (proxyStruct != null) {
            conn.proxy(proxyStruct.ip, proxyStruct.port);
        }
        // 超时设置
        conn.timeout(config.getTimeOut());
        // body大小设置
        conn.maxBodySize(config.getMaxBodySizeBytes());
        //
        Map<String, String> data = config.getDatas();
        if (data != null && data.size() > 0) {
            conn.data(data);
        }
        Map<String, String> cookies = config.getCookies();
        if (cookies != null && cookies.size() > 0) {
            cookies.forEach((k, v) -> conn.cookie(k, v));
        }
        // header
        if (config.getHeaders() != null && config.getHeaders().size() > 0) {
            config.getHeaders().forEach((k, v) -> conn.header(k, v));
        }
        conn.url(url);
        return conn;
    }

    private void sleep(int sleepTime) {
        try {
            Thread.sleep(Math.max(1, sleepTime));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public void close() throws IOException {
        try {
            while (executor.getActiveCount() > 0) {
                sleep(1000);
            }
            executor.shutdown();
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }
}
