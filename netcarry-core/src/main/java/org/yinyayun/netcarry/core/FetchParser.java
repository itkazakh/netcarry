/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core;

import org.jsoup.nodes.Document;

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

    /**
     * 页面解析
     * 
     * @param document
     */
    public abstract void parser(String currentUrl, Document document);
}
