/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.youku;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.yinyayun.netcarry.core.PageFetchExecutor;
import org.yinyayun.netcarry.core.collect.FetchCollector;
import org.yinyayun.netcarry.core.config.NetCarryConfig;
import org.yinyayun.netcarry.core.parser.FetchParser;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * YoukuFetchMain 优酷中关于手机维修页面抓取
 * 
 * @author yinyayun
 */
public class YoukuFetchMain {

    public static void main(String[] args) throws IOException {
        String savePath = "C:/Users/yinyayun/Desktop/自己维修/zjwx.txt";
        String logPath = "C:/Users/yinyayun/Desktop/自己维修/log";
        String mainUrl = "http://www.soku.com";
        int deepPerPage = 10000;
        List<String> carryurls = initCarryUrls();
        //
        FetchCollector<Map<String, String>> collector = new FetchCollector<Map<String, String>>(1000, savePath);
        FetchParser<Map<String, String>> parser = new YoukuPageParser(collector);
        NextPageParserA nextPageParser = new YoukuNextPage(deepPerPage, mainUrl);
        NetCarryConfig config = new NetCarryConfig();
        config.setFetchThreadNumber(3);
        config.setSleepTime(1000);
        new PageFetchExecutor<Map<String, String>>().executor(logPath, carryurls, config, parser, nextPageParser,
                collector);

    }

    public static List<String> initCarryUrls() {
        return Arrays.asList(new String[]{
                // 手机维修
                "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE_orderby_1_limitdate_0?spm=a2h0k.8191407.0.0&site=14&page=1", //
                // 修手机
                // oppo维修
                // iphone维修
                // 苹果维修
                // 小米维修
                // 华为维修
                // 魅族维修
                // 三星维修
                // 手机拆机
                "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E6%8B%86%E6%9C%BA?f=1&kb=020200000000000_%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE_%E6%89%8B%E6%9C%BA%E6%8B%86%E6%9C%BA&_rp=1500978845120CZT",
                // iphone拆机
                "http://www.soku.com/search_video/q_iphone%E6%8B%86%E6%9C%BA?spm=a2h0k.8191407.footeraboutid.1~3!7~A&f=30",
                // 三星拆机
                // 苹果拆机
                // oppo拆机
                // 手机焊接
        });

    }
}
