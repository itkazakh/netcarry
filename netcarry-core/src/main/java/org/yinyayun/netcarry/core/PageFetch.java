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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yinyayun.netcarry.core.config.ConnectionConfig;
import org.yinyayun.netcarry.core.config.ProxyFactoryA.ProxyStruct;

/**
 * PageFetchA.java
 *
 * @author yinyayun
 */
public class PageFetch<T> implements Closeable {
    public final Logger logger = LoggerFactory.getLogger(PageFetch.class);
    /**
     * 已经抓取的URL
     */
    private Set<String> fetchedUrls = new HashSet<String>();
    private ExecutorService executorService;
    private NextPageParserA nextPageParser;
    private FetchParser<T> parser;
    private ConnectionConfig config;
    private int sleepTime;

    public PageFetch(int fetchThradNumber, int sleepTime, FetchParser<T> parser, NextPageParserA nextPageParser,
            ConnectionConfig config) {
        this.sleepTime = sleepTime;
        this.config = config;
        this.parser = parser;
        this.executorService = Executors.newFixedThreadPool(fetchThradNumber);
    }

    public PageFetch(int fetchThradNumber, int sleepTime, FetchParser<T> parser, ConnectionConfig config) {
        this(fetchThradNumber, sleepTime, parser, null, config);
    }

    public void startFetch(List<String> urls) {
        for (String url : urls) {
            doFetch(url);
            sleep(sleepTime);
        }
    }

    /**
     * @param preRule
     * @param commonRule
     */
    private void doFetch(String url) {
        // 防止重复抓取
        if (fetchedUrls.contains(url)) {
            return;
        }
        else {
            fetchedUrls.add(url);
        }
        executorService.submit(() -> {
            try {
                Connection conn = createConnection(url);
                Document document = conn.get();
                System.out.println(document);
                parser.parser(url, document);
                // 解析下一页
                if (nextPageParser == null) {
                    return;
                }
                String nextPage = nextPageParser.nextPage(document);
                if (nextPage != null && nextPage.length() > 0) {
                    doFetch(nextPage);
                    sleep(sleepTime);
                }
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
                e.printStackTrace();
            }
        });
    }

    private void sleep(int sleepTime) {
        try {
            Thread.sleep(Math.max(1, sleepTime));
        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    /**
     * 创建连接
     * 
     * @param url
     * @return
     */
    private Connection createConnection(String url) {
        Connection conn = Jsoup.connect(url);
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
        conn.maxBodySize(config.getMaxBodySizeBytes());
        //
        Map<String, String> data = config.getDatas();
        if (data != null && data.size() > 0) {
            conn.data(data);
        }
        Map<String, String> cookies = config.getCookies();
        if (cookies != null) {
            cookies.forEach((k, v) -> conn.cookie(k, v));
        }
        // header
        config.getHeaders().forEach((k, v) -> conn.header(k, v));
        return conn;
    }

    @Override
    public void close() throws IOException {
        try {
            while (!executorService.awaitTermination(3, TimeUnit.SECONDS));
            executorService.shutdown();
        }
        catch (Exception e) {
            throw new IOException(e);
        }
    }
}
