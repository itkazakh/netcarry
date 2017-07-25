/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.collect;

/**
 * CollectorAction.java 默认我们可能认为抓取的页面信息会直接存储，定义CollectorAction主要为了防止其它情况的出现
 * 
 * @author yinyayun
 */
@FunctionalInterface
public interface CollectorAction<T> {
    public void action(T t);
}
