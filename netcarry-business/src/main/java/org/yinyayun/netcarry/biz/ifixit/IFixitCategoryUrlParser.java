/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
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
 * IfixitNextPageParser 解析https://zh.ifixit.com/Device/Phone、https://zh.ifixit.com/Device/iPhone等页面的子分类
 * 
 * @author yinyayun
 */
public class IFixitCategoryUrlParser extends NextPageParserA {
    private String prefixUrl = "https://zh.ifixit.com";

    public IFixitCategoryUrlParser(int deep) {
        super(deep);
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
    protected List<PageMeta> parser(String url, Document document) {
        List<PageMeta> pages = new ArrayList<PageMeta>();
        Element topContent = document.getElementById("topContent");
        if (topContent != null) {
            Elements subcategorySections = topContent.getElementsByClass("subcategorySection");
            if (subcategorySections.size() > 0) {
                Elements categoryListCells = subcategorySections.get(0).getElementsByClass("categoryListCell");
                for (Element category : categoryListCells) {
                    Elements href = category.getElementsByTag("a");
                    String nextUrl = href.get(0).attr("href");
                    PageMeta pageMeta = new PageMeta(prefixUrl.concat(nextUrl));
                    Element img = href.get(0).getElementsByTag("img").get(0);
                    String title = img.attr("alt");
                    String imgUrl = img.attr("src");
                    pageMeta.addMeta("category", title);
                    pageMeta.addMeta("img", imgUrl);
                    pages.add(pageMeta);
                }
            }
        }
        return pages;
    }

    public static void main(String[] args) throws IOException {
        Document document = Jsoup.parse(FileUtils.readFileToString(new File("data/category-example4.txt")));
        new IFixitCategoryUrlParser(10).parser(null, document);
    }
}
