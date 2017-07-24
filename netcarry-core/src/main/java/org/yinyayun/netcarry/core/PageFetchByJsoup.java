/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core;

import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yinyayun.netcarry.core.config.ConnectionConfig;

/**
 * PageFetch 页面抓取
 * 
 * @author yinyayun
 */
public class PageFetchByJsoup<T> extends PageFetchA<T> {
    public PageFetchByJsoup(int fetchThradNumber, FetchParser<T> parser, ConnectionConfig config) {
        super(fetchThradNumber, parser, config);
    }

    public final Logger logger = LoggerFactory.getLogger(PageFetchByJsoup.class);

    /**
     * @param preRule
     * @param commonRule
     */
    @Override
    protected void doFetch(String url) {
        Runnable run = () -> {
            try {
                Connection conn = Jsoup.connect(url);
                // 连接配置
                connectionConfig(conn);
                Document document = conn.get();
                parser.parser(url, document);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        };
        executorService.submit(run);
    }

    private void connectionConfig(Connection conn) {
        if (conn == null) {
            return;
        }
        // agent设置
        String agent = config.getAgent();
        if (agent != null && agent.length() > 0) {
            conn.userAgent(agent);
        }
        // 超时设置
        conn.timeout(config.getTimeOut());
        //
        Map<String, String> data = config.getDatas();
        if (data != null && data.size() > 0) {
            conn.data(data);
        }
        Map<String, String> cookies = config.getCookies();
        if (cookies != null) {
            cookies.forEach((k, v) -> conn.cookie(k, v));
        }
        // header
        config.getHeaders().forEach((k, v) -> conn.header(k, v));
    }
}
