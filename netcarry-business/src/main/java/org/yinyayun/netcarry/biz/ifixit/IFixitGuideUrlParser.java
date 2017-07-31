/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.ifixit;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.core.dao.PageMeta;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * IFixitPageParser.java 从https://zh.ifixit.com/Device/iPhone_1st_Generation解析教程url
 * 
 * @author yinyayun
 */
public class IFixitGuideUrlParser extends NextPageParserA {
    private String prefixUrl = "https://zh.ifixit.com";

    public IFixitGuideUrlParser(int deep) {
        super(deep);
    }

    // https://zh.ifixit.com/Device/MacBook_Pro_13%22_Unibody_Mid_2009
    @Override
    protected List<PageMeta> parser(String currentUrl, Document document) {
        if (document.getElementsByClass("categoryListCell").size() > 0) {
            return null;
        }
        List<PageMeta> urls = new ArrayList<PageMeta>();
        Elements guides = document.getElementsByClass("blurbListTitle");
        for (Element element : guides) {
            Elements hrefs = element.getElementsByTag("a");
            for (Element href : hrefs) {
                String guide = href.attr("href");
                PageMeta pageMeta = new PageMeta(prefixUrl.concat(guide));
                Elements img = href.getElementsByTag("img");
                String imgUrl = img.get(0).attr("src");
                String title = img.get(0).attr("alt");
                pageMeta.addMeta("img", imgUrl);
                pageMeta.addMeta("category", title);
                urls.add(pageMeta);
            }
        }
        return urls;
    }

    @Override
    public boolean needParserThisPage(String url) {
        return url.startsWith("https://zh.ifixit.com/Device");
    }

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.parse(FileUtils.readFileToString(new File("data/guide-url-example.txt")));
        new IFixitGuideUrlParser(10).parser(null, document);
    }

}
