/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core.parser;

import java.util.List;

import org.jsoup.nodes.Document;
import org.yinyayun.netcarry.core.collect.FetchCollector;
import org.yinyayun.netcarry.core.dao.PageMetas;

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

    public void fetchPaser(PageMetas pageMetas, Document document) {
        List<T> list = parser(pageMetas, document);
        if (list != null && list.size() > 0) {
            list.forEach(x -> collector.add(x));
        }
    }

    public abstract boolean needParser(String url);

    /**
     * 页面解析
     * 
     * @param document
     */
    protected abstract List<T> parser(PageMetas pageCourse, Document document);
}
