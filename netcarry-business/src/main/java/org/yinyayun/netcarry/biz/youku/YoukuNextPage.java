/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.youku;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * YoukuNextPage.java
 *
 * @author yinyayun
 */
public class YoukuNextPage extends NextPageParserA {
    private String mainurl;

    public YoukuNextPage(int deep, String mainurl) {
        super(deep);
        this.mainurl = mainurl;
    }

    @Override
    protected List<String> parser(Document document) {
        Elements elements = document.getElementsByClass("next");
        if (elements.size() > 0) {
            String nextPageUrl = elements.get(0).getElementsByTag("a").get(0).attr("href");
            return Arrays.asList(new String[]{mainurl.concat(nextPageUrl)});
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.parse(FileUtils.readFileToString(new File("data/yk-example.html")));
        Elements elements = document.getElementsByClass("next");
        if (elements.size() > 0) {
            String nextPageUrl = elements.get(0).getElementsByTag("a").get(0).attr("href");
            System.out.println(nextPageUrl);
        }
    }

    @Override
    public boolean needParserThisPage(String url) {
        return true;
    }
}
