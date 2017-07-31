/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.ifixit;

import java.io.IOException;
import java.util.ArrayList;
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
        String savePath = "/Users/yinyayun/Desktop/sjwx/ifixit.txt";
        String logPath = "/Users/yinyayun/Desktop/log";
        String mainUrl = "https://zh.ifixit.com/Guide";
        int deepPerPage = 1000000;
        List<String> carryurls = Arrays.asList(new String[]{mainUrl});
        //
        FetchCollector<GuideContent> collector = new FetchCollector<GuideContent>(2000, savePath);
        FetchParser<GuideContent> parser = new IFixitGuidePagerParser(collector);
        NextPageParserA[] nextPageParser = {new IFixitAllCategoryURLParser(deepPerPage),
                new IFixitCategoryUrlParser(deepPerPage), new IFixitGuideUrlParser(deepPerPage)};
        NetCarryConfig config = new NetCarryConfig();
        config.setSleepTime(10000);
        config.setFetchThreadNumber(5);
        config.setSleepTime(1000);
        new PageFetchExecutor<GuideContent>().executor(logPath, carryurls, config, parser, nextPageParser, collector);

    }
}
