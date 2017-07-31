/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PageMetas.java
 *
 * @author yinyayun
 */
public class PageMetas {
    private String parentUrl;
    private String currentUrl;
    private Map<String, List<String>> metas = new HashMap<String, List<String>>();
    private List<String> jumpUrls = new ArrayList<String>();

    public PageMetas(String parentUrl, String currentUrl) {
        this.parentUrl = parentUrl;
        this.currentUrl = currentUrl;
    }

    public String getParentUrl() {
        return parentUrl;
    }

    public String getCurrentUrl() {
        return currentUrl;
    }

    public void setCurrentUrl(String currentUrl) {
        this.currentUrl = currentUrl;
    }

    public Map<String, List<String>> getMetas() {
        return metas;
    }

    public void addMeta(String metaKey, String metaValue) {
        List<String> values = metas.get(metaKey);
        if (values == null) {
            values = new ArrayList<String>();
            metas.put(metaKey, values);
        }
        values.add(metaValue);
    }

    public void addLastUrl() {
        jumpUrls.add(currentUrl);
    }

    public PageMetas copy() {
        PageMetas pageMetas = new PageMetas(parentUrl, currentUrl);
        pageMetas.metas = metas;
        return pageMetas;
    }

    @Override
    public String toString() {
        return currentUrl;
    }
}
