/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yinyayun.netcarry.core.dao.PageMeta;
import org.yinyayun.netcarry.core.dao.PageMetas;

/**
 * NextPageParserA.java 下一页解析
 * 
 * @author yinyayun
 */
public abstract class NextPageParserA {
    public final static Logger logger = LoggerFactory.getLogger(NextPageParserA.class);
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
    public List<PageMetas> nextPage(PageMetas metas, Document document) {
        int count = counter.incrementAndGet();
        if (count > deep) {
            return null;
        }
        else {
            List<PageMetas> pages = new ArrayList<PageMetas>();
            List<PageMeta> nextPages = parser(metas.getCurrentUrl(), document);
            try {
                for (PageMeta nexPage : nextPages) {
                    PageMetas pageMetas = metas.copy();
                    String url = nexPage.getUrl();
                    pageMetas.addLastUrl();
                    pageMetas.setCurrentUrl(url);
                    nexPage.getMeta().forEach((k, v) -> pageMetas.addMeta(k, v));
                    pages.add(pageMetas);
                }

            }
            catch (Exception e) {
                logger.error(String.format("fetch page:%s error", metas.getCurrentUrl()), e);
            }
            return pages;
        }
    }

    protected abstract List<PageMeta> parser(String url, Document document);
}
