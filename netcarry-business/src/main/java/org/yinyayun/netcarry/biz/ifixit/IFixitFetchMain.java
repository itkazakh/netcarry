/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.ifixit;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.yinyayun.netcarry.core.PageFetchExecutor;
import org.yinyayun.netcarry.core.collect.FetchCollector;
import org.yinyayun.netcarry.core.config.NetCarryConfig;
import org.yinyayun.netcarry.core.parser.FetchParser;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * IFixitFetchMain
 *
 * @author yinyayun
 */
public class IFixitFetchMain {
    public static void main(String[] args) throws IOException {
        String savePath = "C:/Users/yinyayun/Desktop/自己维修/ifixit.txt";
        String logPath = "C:/Users/yinyayun/Desktop/自己维修/log";
        String mainUrl = "https://zh.ifixit.com/Guide";
        int deepPerPage = 1000000;
        List<String> carryurls = Arrays.asList(new String[]{"https://zh.ifixit.com/Guide"});
        //
        FetchCollector<GuideContent> collector = new FetchCollector<GuideContent>(2000, savePath);
        FetchParser<GuideContent> parser = new IFixitGuidePagerParser(collector);
        NextPageParserA[] nextPageParser = {new IFixitAllCategoryURLParser(deepPerPage),
                new IFixitCategoryUrlParser(deepPerPage), new IFixitGuideUrlParser(deepPerPage)};
        NetCarryConfig config = new NetCarryConfig();
        config.setFetchThreadNumber(1);
        config.setSleepTime(1000);
        new PageFetchExecutor<GuideContent>().executor(logPath, carryurls, config, parser, nextPageParser, collector);

    }
}
