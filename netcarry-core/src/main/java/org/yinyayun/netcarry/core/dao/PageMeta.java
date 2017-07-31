/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.dao;

import java.util.HashMap;
import java.util.Map;

/**
 * PageMeta.java 页面历程，记录业务跳转的过程
 * 
 * @author yinyayun
 */
public class PageMeta {
    private String url;
    private Map<String, String> meta = new HashMap<String, String>();

    public PageMeta(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public Map<String, String> getMeta() {
        return meta;
    }

    public void addMeta(Map<String, String> meta) {
        this.meta = meta;
    }

}
