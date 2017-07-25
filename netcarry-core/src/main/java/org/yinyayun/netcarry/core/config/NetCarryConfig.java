/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.config;

import java.util.HashMap;
import java.util.Map;

import org.yinyayun.netcarry.core.config.ProxyFactoryA.ProxyStruct;

/**
 * ConnectionConfig.java 抓取程序配置、以及HTTP连接配置
 * 
 * @author yinyayun
 */
public class NetCarryConfig {
    // 默认抓取的线程数
    private int fetchThreadNumber = 1;
    // 每次抓完后的休眠时间
    private int sleepTime = 100;
    // 待抓取队列大小设置
    private int tobeCarryQueueSize = 1000;
    private int timeOut = 3000;
    private int maxBodySizeBytes = 1024 * 1024 * 3;
    private ProxyFactoryA proxy;
    private AgentFactoryA agent;
    private Map<String, String> headers = new HashMap<String, String>();
    private Map<String, String> datas = new HashMap<String, String>();
    private Map<String, String> cookies = new HashMap<String, String>();

    public void setAgentFactory(AgentFactoryA agent) {
        this.agent = agent;
    }

    public void setTimeOut(int timeOut) {
        this.timeOut = timeOut;
    }

    public void setProxyFactory(ProxyFactoryA proxy) {
        this.proxy = proxy;
    }

    public void addData(String key, String value) {
        datas.put(key, value);
    }

    public void setDatas(Map<String, String> datas) {
        this.datas = datas;
    }

    public void addCookie(String key, String value) {
        this.cookies.put(key, value);
    }

    public void setCookies(Map<String, String> cookies) {
        this.cookies = cookies;
    }

    public String getAgent() {
        return agent == null ? null : agent.takeAgent();
    }

    public int getTimeOut() {
        return timeOut;
    }

    public ProxyStruct getProxy() {
        return proxy == null ? null : proxy.takeProxy();
    }

    public Map<String, String> getDatas() {
        return datas;
    }

    public Map<String, String> getCookies() {
        return cookies;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public int getMaxBodySizeBytes() {
        return maxBodySizeBytes;
    }

    public void setMaxBodySizeBytes(int maxBodySizeBytes) {
        this.maxBodySizeBytes = maxBodySizeBytes;
    }

    public int getFetchThreadNumber() {
        return fetchThreadNumber;
    }

    public void setFetchThreadNumber(int fetchThreadNumber) {
        this.fetchThreadNumber = fetchThreadNumber;
    }

    public int getSleepTime() {
        return sleepTime;
    }

    public void setSleepTime(int sleepTime) {
        this.sleepTime = sleepTime;
    }

    public int getTobeCarryQueueSize() {
        return tobeCarryQueueSize;
    }

    public void setTobeCarryQueueSize(int tobeCarryQueueSize) {
        this.tobeCarryQueueSize = tobeCarryQueueSize;
    }

}
