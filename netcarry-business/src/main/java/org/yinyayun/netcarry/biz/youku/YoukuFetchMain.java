/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.youku;

import java.util.Arrays;
import java.util.List;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.core.FetchCollector;
import org.yinyayun.netcarry.core.FetchParser;
import org.yinyayun.netcarry.core.PageFetch;
import org.yinyayun.netcarry.core.PreURLFetch;

/**
 * YoukuFetchMain
 *
 * @author yinyayun
 */
public class YoukuFetchMain {
    public static List<String> urls = Arrays.asList(new String[]{
            "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE_orderby_1_limitdate_0?spm=a2h0k.8191407.0.0&site=14&page=1"});

    public static void main(String[] args) {
        FetchCollector<String> collector = new FetchCollector<String>(1000);
        FetchParser<String> parser = new YoukuPageParser(collector);

        PreURLFetch preURLFetch = (x, y) -> {
            Elements elements = y.getElementsByClass("current");
            Elements href = elements.get(0).getElementsByTag("a");
            String preUrl = href.get(0).attr("href");
            int pos = x.lastIndexOf("?");
            return x.substring(0, pos).concat("/").concat(preUrl).concat(x.substring(pos));
        };
        PageFetch<String> pageFetch = new PageFetch<String>(1, preURLFetch, parser);
        pageFetch.startFetch(urls);
    }
}
