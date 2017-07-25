/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.youku;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.core.NextPageParserA;

/**
 * YoukuNextPage.java
 *
 * @author yinyayun
 */
public class YoukuNextPage extends NextPageParserA {
    public YoukuNextPage(int deep, String mainurl) {
        super(deep);
    }

    @Override
    protected String parser(Document document) {
        Elements elements = document.getElementsByClass("next");
        if (elements == null || elements.size() == 0) {
            return null;
        }
        String nextPage = elements.get(0).absUrl("href");
        return nextPage;
    }

}
