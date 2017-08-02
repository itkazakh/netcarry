/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.demo;

import java.util.List;

import org.jsoup.nodes.Document;
import org.yinyayun.netcarry.core.collect.FetchCollector;
import org.yinyayun.netcarry.core.dao.PageMetas;
import org.yinyayun.netcarry.core.parser.FetchParser;

/**
 * PageParserDemo.java
 *
 * @author yinyayun
 */
public class PageParserDemo extends FetchParser<String> {
    /**
     * collector为收集器,负责页面解析结果的收集存储
     * 
     * @param collector
     */
    public PageParserDemo(FetchCollector<String> collector) {
        super(collector);
    }

    /**
     * 该URL是否满足解析规则
     */
    @Override
    public boolean needParser(String url) {
        return false;
    }

    /**
     * 页面解析
     * 
     * @param page 该页面的父页面的信息
     */
    @Override
    protected List<String> parser(PageMetas page, Document document) {
        return null;
    }

}
