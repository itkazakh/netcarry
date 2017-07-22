/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yinyayun.netcarry.core.config.AgentFactory;

/**
 * PageFetch 页面抓取
 * 
 * @author yinyayun
 */
public class PageFetch<T> {
    public final Logger logger = LoggerFactory.getLogger(PageFetch.class);

    /**
     * 已经抓取的URL
     */
    private volatile Set<String> fetchedUrls = new HashSet<String>();
    private AgentFactory agentFactory;
    private ExecutorService executorService;
    private PreURLFetch preFetch;
    private FetchParser<T> parser;
    private int timeOut = 3000;

    public PageFetch(int fetchThradNumber, PreURLFetch preFetch, FetchParser<T> parser) {
        this(fetchThradNumber, 3000, preFetch, parser, new AgentFactory());
    }

    public PageFetch(int fetchThradNumber, int timeOut, PreURLFetch preFetch, FetchParser<T> parser,
            AgentFactory agentFactory) {
        this.parser = parser;
        this.timeOut = timeOut;
        this.preFetch = preFetch;
        this.agentFactory = agentFactory;
        this.executorService = Executors.newFixedThreadPool(fetchThradNumber);
    }

    public void startFetch(List<String> urls) {
        for (String url : urls) {
            doFetch(url, preFetch);
        }
    }

    /**
     * @param lay 当前深度
     * @param preRule
     * @param commonRule
     */
    private void doFetch(String url, PreURLFetch preFetch) {
        try {
            // 防止重复抓取
            if (fetchedUrls.contains(url)) {
                return;
            }
            else {
                fetchedUrls.add(url);
            }
            //
            Connection conn = Jsoup.connect(url);
            // 连接配置
            connectionConfig(conn);
            Document document = conn.get();
            if (preFetch != null) {
                doFetch(preFetch.fetchPreUrl(url, document), null);
            }
            Runnable run = () -> {
                parser.parser(url, document);
            };
            executorService.submit(run);

        }
        catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    private void connectionConfig(Connection conn) {
        String agent = agentFactory.takeAgent();
        if (agent != null && agent.length() > 0) {
            conn.userAgent(agent);
        }
        conn.timeout(timeOut);
    }
}
