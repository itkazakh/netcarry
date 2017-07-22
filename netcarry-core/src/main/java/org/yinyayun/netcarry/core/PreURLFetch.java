/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core;

import org.jsoup.nodes.Document;

/**
 * PreURLFetch 前置URL的提取
 * 
 * @author yinyayun
 */
@FunctionalInterface
public interface PreURLFetch {
    public String fetchPreUrl(String currentUrl, Document doc);
}
