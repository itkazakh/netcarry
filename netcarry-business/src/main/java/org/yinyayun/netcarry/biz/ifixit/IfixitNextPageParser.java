/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.ifixit;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * IfixitNextPageParser
 *
 * @author yinyayun
 */
public class IfixitNextPageParser extends NextPageParserA {
    private String prefixUrl;

    public IfixitNextPageParser(int deep, String prefixUrl) {
        super(deep);
        this.prefixUrl = prefixUrl;
    }

    /**
     * 当且仅当以https://zh.ifixit.com/Device为前缀的页面才进行下一页的抓取
     */
    @Override
    public boolean needParserThisPage(String url) {
        return url.startsWith("https://zh.ifixit.com/Device");
    }

    // https://zh.ifixit.com/Device/Phone

    @Override
    protected List<String> parser(Document document) {
        List<String> urls = new ArrayList<String>();
        Elements elements = document.getElementsByClass("categoryListCell");
        for (Element category : elements) {
            String url = category.getElementsByTag("a").get(0).attr("href");
            urls.add(prefixUrl.concat(url));
        }
        return urls;
    }

}
