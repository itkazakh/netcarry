/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.biz.youku;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.yinyayun.netcarry.core.FetchCollector;
import org.yinyayun.netcarry.core.FetchParser;
import org.yinyayun.netcarry.core.PageFetchA;
import org.yinyayun.netcarry.core.PageFetchByJsoup;
import org.yinyayun.netcarry.core.config.ConnectionConfig;
import org.yinyayun.netcarry.core.config.DefaultAgentFactory;

/**
 * YoukuFetchMain
 *
 * @author yinyayun
 */
public class YoukuFetchMain {
    public static List<String> urls = Arrays.asList(new String[]{
            "http://www.soku.com/search_video/q_%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE_orderby_1_limitdate_0?spm=a2h0k.8191407.0.0&site=14&page=1"});

    public static void main(String[] args) {
        YoukuFetchMain main = new YoukuFetchMain();
        FetchCollector<String> collector = new FetchCollector<String>(1000);
        FetchParser<String> parser = new YoukuPageParser(collector);
        //
        Map<String, String> cookie = main.buildCookie();
        ConnectionConfig config = new ConnectionConfig();
        config.setCookies(cookie);
        config.setAgentFactory(new DefaultAgentFactory());
        config.setProxyFactory(new ProxyFactory());
        PageFetchA<String> pageFetch = new PageFetchByJsoup<String>(1, parser, config);
        pageFetch.startFetch(urls);
    }

    public Map<String, String> buildCookie() {
        Map<String, String> cookie = new HashMap<String, String>();
        cookie.put("JSESSIONID", "B5B781AAD1E7A35636F1DB3BE087E4D7");
        cookie.put("_log_check", "1");
        cookie.put("SK_UUID", "[{\"q\":\"%E6%89%8B%E6%9C%BA%E7%BB%B4%E4%BF%AE\",\"p\":\"shoujiweixiu\"}]");
        cookie.put("__ayft", "1500884434711");
        cookie.put("__aysid", "1500884434711gO5");

        cookie.put("__arpvid", "1500884434711C0ymPq-1500884434720");
        cookie.put("__ayscnt", "1");
        cookie.put("__aypstp", "1");
        cookie.put("__ayspstp", "1");

        cookie.put("SOKUSESSID", "15008844343650aV");
        cookie.put("cna", "zDj8EZpkqF8CATrwGsvnGWNV");

        cookie.put("_uab_collina", "150088443555979884260435");
        cookie.put("_umdata",
                "6AF5B463492A874DC6BFC30758FD7617FB649D34539F1D14DF1C07F7DE7081686AD6ACFF2D01FF50CD43AD3E795C914CB6E8E238CE624EB089B05ABB56BA30E0");
        return cookie;
    }
}
