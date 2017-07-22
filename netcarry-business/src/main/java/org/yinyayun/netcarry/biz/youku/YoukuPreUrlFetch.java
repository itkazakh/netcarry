/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.youku;

import org.jsoup.nodes.Document;
import org.yinyayun.netcarry.core.PreURLFetch;

/**
 * YoukuPreUrlFetch 因为页面异步加载的缘故，所以需要有限加载对应的URL
 * 
 * @author yinyayun
 */
public class YoukuPreUrlFetch implements PreURLFetch {

    @Override
    public String fetchPreUrl(String currentUrl, Document doc) {
        return null;
    }

}
