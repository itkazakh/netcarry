/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core.parser;

import java.util.List;

import org.jsoup.nodes.Document;
import org.yinyayun.netcarry.core.collect.FetchCollector;

/**
 * FetchParser 抓取页面的解析
 *
 * @author yinyayun
 */
public abstract class FetchParser<T> {
    private FetchCollector<T> collector;

    public FetchParser(FetchCollector<T> collector) {
        this.collector = collector;
    }

    public void fetchPaser(String currentUrl, Document document) {
        List<T> list = parser(currentUrl, document);
        if (list != null && list.size() > 0) {
            list.forEach(x -> collector.add(x));
        }
    }

    /**
     * 页面解析
     * 
     * @param document
     */
    protected abstract List<T> parser(String currentUrl, Document document);
}
