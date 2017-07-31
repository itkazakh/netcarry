/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.ifixit;

import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.core.dao.PageMeta;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * IFixitAllCategoryURLParser.java 起始页中所有目录的抓取
 * 
 * @author yinyayun
 */
public class IFixitAllCategoryURLParser extends NextPageParserA {
    private String prefix = "https://zh.ifixit.com";

    public IFixitAllCategoryURLParser(int deep) {
        super(deep);
    }

    @Override
    public boolean needParserThisPage(String url) {
        return url.equals("https://zh.ifixit.com/Guide");
    }

    @Override
    protected List<PageMeta> parser(String url, Document document) {
        List<PageMeta> pageMetas = new ArrayList<PageMeta>();
        Elements categories = document.getElementsByClass("sub-categories");
        for (Element categorie : categories) {
            Elements hrefs = categorie.getElementsByTag("a");
            for (Element href : hrefs) {
                String nextPage = href.attr("href");
                String txt = href.getElementsByClass("sub-category-title-text").get(0).text();
                PageMeta pageMeta = new PageMeta(prefix.concat(nextPage));
                pageMeta.addMeta("category", txt);
                pageMeta.addMeta("img", "NAN");
                pageMetas.add(pageMeta);
            }
        }
        return pageMetas;
    }

}
