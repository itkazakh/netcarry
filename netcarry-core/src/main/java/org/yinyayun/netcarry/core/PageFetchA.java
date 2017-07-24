/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yinyayun.netcarry.core.config.ConnectionConfig;

/**
 * PageFetchA.java
 *
 * @author yinyayun
 */
public abstract class PageFetchA<T> {
    public final Logger logger = LoggerFactory.getLogger(PageFetchByJsoup.class);
    /**
     * 已经抓取的URL
     */
    protected Set<String> fetchedUrls = new HashSet<String>();
    protected ExecutorService executorService;
    protected FetchParser<T> parser;
    protected ConnectionConfig config;

    public PageFetchA(int fetchThradNumber, FetchParser<T> parser, ConnectionConfig config) {
        this.config = config;
        this.parser = parser;
        this.executorService = Executors.newFixedThreadPool(fetchThradNumber);
    }

    public void startFetch(List<String> urls) {
        for (String url : urls) {
            // 防止重复抓取
            if (fetchedUrls.contains(url)) {
                return;
            }
            else {
                fetchedUrls.add(url);
            }
            doFetch(url);
        }
    }

    /**
     * @param lay 当前深度
     * @param preRule
     * @param commonRule
     */
    protected abstract void doFetch(String url);
}
