/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.parser;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;

/**
 * NextPageParserA.java 下一页解析
 * 
 * @author yinyayun
 */
public abstract class NextPageParserA {
    private final int deep;
    private AtomicInteger counter = new AtomicInteger(0);

    public NextPageParserA(int deep) {
        this.deep = deep;
    }

    /**
     * 给定url，可由具体的实现类决定是否要从该url中获取下一页
     * 
     * @param url
     * @return
     */
    public abstract boolean needParserThisPage(String url);

    /**
     * 下一页获取
     * 
     * @param document
     * @return
     */
    public List<String> nextPage(Document document) {
        int count = counter.incrementAndGet();
        if (count > deep) {
            return null;
        }
        else {
            return parser(document);
        }
    }

    protected abstract List<String> parser(Document document);
}
