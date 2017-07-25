/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.youku;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.yinyayun.netcarry.core.collect.FetchCollector;
import org.yinyayun.netcarry.core.parser.FetchParser;

/**
 * YoukuPageParser 优酷页面解析
 * 
 * @author yinyayun
 */
public class YoukuPageParser extends FetchParser<Map<String, String>> {
    private Set<String> links = Collections.synchronizedSet(new HashSet<String>());

    public YoukuPageParser(FetchCollector<Map<String, String>> collector) {
        super(collector);
    }

    @Override
    public List<Map<String, String>> parser(String currentUrl, Document document) {
        List<Map<String, String>> values = new ArrayList<Map<String, String>>();
        Elements elements = document.getElementsByClass("v-link");
        for (Element element : elements) {
            Map<String, String> map = new HashMap<String, String>();
            Elements aElements = element.getElementsByTag("a");
            String title = aElements.get(0).attr("title");
            String href = aElements.get(0).attr("href");
            if (!links.contains(href)) {
                map.put("title", title);
                map.put("href", href);
                values.add(map);
                links.add(href);
            }
        }
        return values;
    }

    public static void main(String[] args) throws IOException {
        List<Map<String, String>> values = new ArrayList<Map<String, String>>();
        Document document = Jsoup.parse(FileUtils.readFileToString(new File("data/yk-example.html")));
        Elements elements = document.getElementsByClass("v-link");
        for (Element element : elements) {
            Map<String, String> map = new HashMap<String, String>();
            Elements aElements = element.getElementsByTag("a");
            String title = aElements.get(0).attr("title");
            String href = aElements.get(0).attr("href");
            map.put("title", title);
            map.put("href", href);
            values.add(map);
        }
    }
}
