/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.biz.youku;

import org.yinyayun.netcarry.core.config.ProxyFactoryA;

/**
 * ProxyFactory.java
 *
 * @author yinyayun
 */
public class ProxyFactory extends ProxyFactoryA {

    @Override
    public ProxyStruct takeProxy() {
        return new ProxyStruct("192.168.16.187", 8080, "yinyayun", "123456789");
    }

}
