/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.demo;

import java.util.List;

import org.jsoup.nodes.Document;
import org.yinyayun.netcarry.core.dao.PageMeta;
import org.yinyayun.netcarry.core.parser.NextPageParserA;

/**
 * NextPageURLParser.java
 * 
 * @author yinyayun
 */
public class NextPageURLParserDemo extends NextPageParserA {
    /**
     * deep参数定义的有些问题，目前不是当做深度再使用，做大抓取页面数
     * 
     * @param deep
     */
    public NextPageURLParserDemo(int deep) {
        super(deep);
    }

    /**
     * 该页面是否满足进一步抓取页面的要求
     */
    @Override
    public boolean needParserThisPage(String url) {
        return false;
    }

    /**
     * 解析出该页面中哪一些页面要作抓取
     */
    @Override
    protected List<PageMeta> parser(String url, Document document) {
        return null;
    }
}
