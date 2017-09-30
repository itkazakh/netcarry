/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core;

import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.helper.HttpConnection;
import org.yinyayun.netcarry.core.config.NetCarryConfig;
import org.yinyayun.netcarry.core.config.ProxyFactoryA.ProxyStruct;

/**
 * ConnectionFactory.java
 *
 * @author yinyayun
 */
public class ConnectionFactory {
    private NetCarryConfig config;

    public ConnectionFactory(NetCarryConfig config) {
        this.config = config;
    }

    public Connection createConnection(String url) {
        Connection conn = HttpConnection.connect(url);
        //
        conn.ignoreContentType(config.isIgnoreContentType());
        // agent设置
        String agent = config.getAgent();
        if (agent != null && agent.length() > 0) {
            conn.userAgent(agent);
        }
        ProxyStruct proxyStruct = config.getProxy();
        if (proxyStruct != null) {
            conn.proxy(proxyStruct.ip, proxyStruct.port);
        }
        // 超时设置
        conn.timeout(config.getTimeOut());
        // body大小设置
        conn.maxBodySize(config.getMaxBodySizeBytes());
        //
        Map<String, String> data = config.getDatas();
        if (data != null && data.size() > 0) {
            conn.data(data);
        }
        Map<String, String> cookies = config.getCookies();
        if (cookies != null && cookies.size() > 0) {
            cookies.forEach((k, v) -> conn.cookie(k, v));
        }
        // header
        if (config.getHeaders() != null && config.getHeaders().size() > 0) {
            config.getHeaders().forEach((k, v) -> conn.header(k, v));
        }
        conn.url(url);
        return conn;
    }

}
