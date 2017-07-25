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
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yinyayun.netcarry.core.config.NetCarryConfig;
import org.yinyayun.netcarry.core.config.ProxyFactoryA.ProxyStruct;
import org.yinyayun.netcarry.core.parser.FetchParser;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * PageFetchA.java 页面的抓取逻辑
 * 
 * @author yinyayun
 */
public class PageFetch<T> implements Closeable {
    public final Logger logger = LoggerFactory.getLogger(PageFetch.class);
    // 待收抓取url队列
    private LinkedBlockingQueue<String> tobeCarrayURLQueue;
    /**
     * 已经抓取的URL
     */
    private Set<String> fetchedUrls = new HashSet<String>();
    private ThreadPoolExecutor executor;
    private NextPageParserA nextPageParser;
    private FetchParser<T> parser;
    private NetCarryConfig config;

    public PageFetch(FetchParser<T> parser, NextPageParserA nextPageParser, NetCarryConfig config) {
        this.config = config;
        this.parser = parser;
        this.tobeCarrayURLQueue = new LinkedBlockingQueue<String>(config.getTobeCarryQueueSize());
        this.executor = new ThreadPoolExecutor(config.getFetchThreadNumber(), config.getFetchThreadNumber() * 2, 0L,
                TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>());
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
            addURL(url);
        }
    }

    /**
     * 添加至带抓取队列
     * 
     * @param url
     * @throws InterruptedException
     */
    private void addURL(String url) {
        try {
            if (!fetchedUrls.contains(url)) {
                logger.info("添加至待抓取队列:{}", url);
                tobeCarrayURLQueue.put(url);
                fetchedUrls.add(url);
            }
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    /**
     * 从待抓取队列中获取URL
     * 
     * @return
     */
    private String takeURL() {
        try {
            sleep(config.getSleepTime());
            String url = tobeCarrayURLQueue.take();
            logger.info("从待抓取队列获取:{}", url);
            return url;
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
            try {
                String url = takeURL();
                Connection conn = createConnection(url);
                Document document = conn.get();
                // Document document = Jsoup.parse(new URL(url), 3000);
                parser.fetchPaser(url, document);
                // 解析下一页
                if (nextPageParser == null) {
                    return;
                }
                String nextPage = nextPageParser.nextPage(document);
                if (nextPage != null && nextPage.length() > 0) {
                    addURL(nextPage);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
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
