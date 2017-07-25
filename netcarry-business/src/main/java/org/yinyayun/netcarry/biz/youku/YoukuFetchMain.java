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
        int deepPerPage = 100;
        List<String> carryurls = initCarryUrls();
        //
        FetchCollector<Map<String, String>> collector = new FetchCollector<Map<String, String>>(1000, savePath);
        FetchParser<Map<String, String>> parser = new YoukuPageParser(collector);
        NextPageParserA nextPageParser = new YoukuNextPage(deepPerPage, mainUrl);
        NetCarryConfig config = new NetCarryConfig();
        new PageFetchExecutor<Map<String, String>>().executor(logPath, carryurls, config, parser, nextPageParser,
                collector);

    }

    public static List<String> initCarryUrls() {
        return Arrays.asList(new String[]{
                "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE_orderby_1_limitdate_0?spm=a2h0k.8191407.0.0&site=14&page=1"});
    }
}
