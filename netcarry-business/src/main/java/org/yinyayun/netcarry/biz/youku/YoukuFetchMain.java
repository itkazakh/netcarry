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
        String savePath = "/Users/yinyayun/Desktop/sjwx/zjwx.txt";
        String logPath = "/Users/yinyayun/Desktop/log";
        String mainUrl = "http://www.soku.com";
        int deepPerPage = 1000000;
        List<String> carryurls = initCarryUrls();
        //
        FetchCollector<Map<String, String>> collector = new FetchCollector<Map<String, String>>(2000, savePath);
        FetchParser<Map<String, String>> parser = new YoukuPageParser(collector);
        NextPageParserA[] nextPageParser = {new YoukuNextPage(deepPerPage, mainUrl)};
        NetCarryConfig config = new NetCarryConfig();
        config.setFetchThreadNumber(carryurls.size());
        config.setSleepTime(1000);
        new PageFetchExecutor<Map<String, String>>().executor(logPath, carryurls, config, parser, nextPageParser,
                collector);

    }

    public static List<String> initCarryUrls() {
        return Arrays.asList(new String[]{
                // 手机维修
                "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE_orderby_1_limitdate_0?spm=a2h0k.8191407.0.0&site=14&page=1", //
                // 修手机
                "http://www.soku.com/search_video/q_%20%E4%BF%AE%E6%89%8B%E6%9C%BA?f=1&kb=020200000000000_%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE_%E4%BF%AE%E6%89%8B%E6%9C%BA&_rp=1500992232830kqE",
                // oppo维修
                "http://www.soku.com/search_video/q_oppo%E7%BB%B4%E4%BF%AE?f=1&kb=020200000000000_+%E4%BF%AE%E6%89%8B%E6%9C%BA_oppo%E7%BB%B4%E4%BF%AE&_rp=1500992243115gye",
                // iphone维修
                "http://www.soku.com/search_video/q_iphone%E7%BB%B4%E4%BF%AE?f=1&kb=020200000000000_oppo%E7%BB%B4%E4%BF%AE_iphone%E7%BB%B4%E4%BF%AE&_rp=1500992262571POA",
                // 苹果维修
                "http://www.soku.com/search_video/q_%E8%8B%B9%E6%9E%9C%E7%BB%B4%E4%BF%AE?f=1&kb=020200000000000_iphone%E7%BB%B4%E4%BF%AE_%E8%8B%B9%E6%9E%9C%E7%BB%B4%E4%BF%AE&_rp=1500992278160Yy0",
                // 小米维修
                "http://www.soku.com/search_video/q_%E5%B0%8F%E7%B1%B3%E7%BB%B4%E4%BF%AE?f=1&kb=020200000000000_%E8%8B%B9%E6%9E%9C%E7%BB%B4%E4%BF%AE_%E5%B0%8F%E7%B1%B3%E7%BB%B4%E4%BF%AE&_rp=1500992296326Q1c",
                // 华为维修
                "http://www.soku.com/search_video/q_%E5%8D%8E%E4%B8%BA%E7%BB%B4%E4%BF%AE?f=1&kb=020200000000000_%E5%B0%8F%E7%B1%B3%E7%BB%B4%E4%BF%AE_%E5%8D%8E%E4%B8%BA%E7%BB%B4%E4%BF%AE&_rp=1500992311308F6a",
                // 魅族维修
                "http://www.soku.com/search_video/q_%E9%AD%85%E6%97%8F%E7%BB%B4%E4%BF%AE?f=1&kb=020200000000000_%E5%8D%8E%E4%B8%BA%E7%BB%B4%E4%BF%AE_%E9%AD%85%E6%97%8F%E7%BB%B4%E4%BF%AE&_rp=15009923218299Eu",
                // 三星维修
                "http://www.soku.com/search_video/q_%E4%B8%89%E6%98%9F%E7%BB%B4%E4%BF%AE?f=1&kb=020200000000000_%E4%B8%89%E6%98%9F%E7%BB%B4%E4%BF%AE_%E4%B8%89%E6%98%9F%E7%BB%B4%E4%BF%AE&_rp=1500992356275Ky0",
                // 手机拆机
                "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E6%8B%86%E6%9C%BA?f=1&kb=020200000000000_%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE_%E6%89%8B%E6%9C%BA%E6%8B%86%E6%9C%BA&_rp=1500978845120CZT",
                // iphone拆机
                "http://www.soku.com/search_video/q_iphone%E6%8B%86%E6%9C%BA?spm=a2h0k.8191407.footeraboutid.1~3!7~A&f=30",
                // 三星拆机
                "http://www.soku.com/search_video/q_%E4%B8%89%E6%98%9F%E6%8B%86%E6%9C%BA?f=1&kb=020200000000000_%E4%B8%89%E6%98%9F%E6%8B%86%E6%9C%BA_%E4%B8%89%E6%98%9F%E6%8B%86%E6%9C%BA&_rp=1500992366784dKg",
                // 苹果拆机
                "http://www.soku.com/search_video/q_%E8%8B%B9%E6%9E%9C%E6%8B%86%E6%9C%BA?f=1&kb=020200000000000_%E8%8B%B9%E6%9E%9C%E6%8B%86%E6%9C%BA_%E8%8B%B9%E6%9E%9C%E6%8B%86%E6%9C%BA&_rp=1500992387833MlS",
                // oppo拆机
                "http://www.soku.com/search_video/q_oppo%E6%8B%86%E6%9C%BA?f=1&kb=020200000000000_%E8%8B%B9%E6%9E%9C%E6%8B%86%E6%9C%BA_oppo%E6%8B%86%E6%9C%BA&_rp=1500992396407EHV",
                // 小米拆机
                "http://www.soku.com/search_video/q_%E5%B0%8F%E7%B1%B3%E6%8B%86%E6%9C%BA?f=1&kb=020200000000000_oppo%E6%8B%86%E6%9C%BA_%E5%B0%8F%E7%B1%B3%E6%8B%86%E6%9C%BA&_rp=1500992416673RN4",
                // 华为拆机
                "http://www.soku.com/search_video/q_%E5%8D%8E%E4%B8%BA%E6%8B%86%E6%9C%BA?f=1&kb=020200000000000_%E5%B0%8F%E7%B1%B3%E6%8B%86%E6%9C%BA_%E5%8D%8E%E4%B8%BA%E6%8B%86%E6%9C%BA&_rp=1500992460158M3L",
                // 魅族拆机
                "http://www.soku.com/search_video/q_%E9%AD%85%E6%97%8F%E6%8B%86%E6%9C%BA?f=1&kb=020200000000000_%E5%8D%8E%E4%B8%BA%E6%8B%86%E6%9C%BA_%E9%AD%85%E6%97%8F%E6%8B%86%E6%9C%BA&_rp=1500992514999408",
                // 手机焊接
                "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E7%84%8A%E6%8E%A5?f=1&kb=020200000000000_%E9%AD%85%E6%97%8F%E6%8B%86%E6%9C%BA_%E6%89%8B%E6%9C%BA%E7%84%8A%E6%8E%A5&_rp=15009925322321pi",
                // 手机拆机工具
                "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E6%8B%86%E6%9C%BA%E5%B7%A5%E5%85%B7?f=1&kb=020200000000000_%E6%8B%86%E6%9C%BA%E5%B7%A5%E5%85%B7_%E6%89%8B%E6%9C%BA%E6%8B%86%E6%9C%BA%E5%B7%A5%E5%85%B7&_rp=1500992590895oIU"});

    }
}
