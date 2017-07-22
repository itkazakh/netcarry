/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.youku;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.core.FetchCollector;
import org.yinyayun.netcarry.core.FetchParser;

/**
 * YoukuPageParser 优酷页面解析
 * 
 * @author yinyayun
 */
public class YoukuPageParser extends FetchParser<String> {
    private String crawlerUrl = "http://v.youku.com/v_show";

    public YoukuPageParser(FetchCollector<String> collector) {
        super(collector);
    }

    @Override
    public void parser(String currentUrl, Document document) {
        Elements elements = document.getElementsByClass("a");
        for (Element element : elements) {
            Elements href = element.getElementsByAttribute("href");
            if (href.get(0).text().startsWith(crawlerUrl)) {
                Elements title = element.getElementsByAttribute("title");
            }
        }

    }

}
