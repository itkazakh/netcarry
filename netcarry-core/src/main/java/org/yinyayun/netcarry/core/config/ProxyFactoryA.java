/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.config;

/**
 * ProxyFactory.java 代理工厂
 * 
 * @author yinyayun
 */
public abstract class ProxyFactoryA {

    public abstract ProxyStruct takeProxy();

    public class ProxyStruct {
        public String ip;
        public int port;
        public String user;
        public String passwd;

        public ProxyStruct(String ip, int port) {
            this(ip, port, null, null);
        }

        public ProxyStruct(String ip, int port, String user, String passwd) {
            this.ip = ip;
            this.port = port;
            this.user = user;
            this.passwd = passwd;
        }
    }
}
