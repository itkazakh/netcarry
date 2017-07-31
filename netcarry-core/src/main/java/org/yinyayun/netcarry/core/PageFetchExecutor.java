/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.PropertyConfigurator;
import org.yinyayun.netcarry.core.collect.FetchCollector;
import org.yinyayun.netcarry.core.config.NetCarryConfig;
import org.yinyayun.netcarry.core.parser.FetchParser;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * PageFetchExecutor.java 页面抓取执行入口
 * 
 * @author yinyayun
 */
public class PageFetchExecutor<T> {

    public void executor(String logPath, List<String> carryUrls, NetCarryConfig config, FetchParser<T> fetchParser,
            NextPageParserA[] nextPageParsers, FetchCollector<T> collector) throws IOException {
        initLog4j(logPath);
        try (PageFetch<T> pageFetch = new PageFetch<T>(fetchParser, nextPageParsers, config)) {
            pageFetch.startFetch(carryUrls);
        }
    }

    /**
     * 日志文件加载
     * 
     * @param logConf
     * @param logPath
     */
    public void initLog4j(String logPath) {
        File fold = new File(logPath);
        if (!fold.exists()) {
            fold.mkdirs();
        }
        try (InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("log4j.properties")) {
            Properties properties = new Properties();
            properties.load(inputStream);
            properties.setProperty("log4j.file.dir", logPath);
            PropertyConfigurator.configure(properties);
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
