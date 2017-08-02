/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.demo;

import java.io.IOException;
import java.util.Arrays;

import org.yinyayun.netcarry.core.PageFetchExecutor;
import org.yinyayun.netcarry.core.collect.FetchCollector;
import org.yinyayun.netcarry.core.config.NetCarryConfig;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * CarrayMainDemo.java 抓取程序的入口
 * 
 * @author yinyayun
 */
public class CarrayMainDemo {
    public static void main(String[] args) throws IOException {
        // 结果保存路径
        String savePath = "";
        // 日志路径
        String logPath = "logDir";
        // 抓取页面的入口
        String[] carryUrls = {};
        // 抓取应用的相关配置，包括连接配置信息，线程数等等
        NetCarryConfig config = new NetCarryConfig();
        config.setFetchThreadNumber(5);
        //
        PageFetchExecutor<String> main = new PageFetchExecutor<String>();
        FetchCollector<String> collector = new FetchCollector<String>(1000, savePath);
        PageParserDemo pageParser = new PageParserDemo(collector);
        NextPageParserA nextPageParsers = new NextPageURLParserDemo(Integer.MAX_VALUE);
        main.execute(logPath, Arrays.asList(carryUrls), config, pageParser, new NextPageParserA[]{nextPageParsers});
    }
}
