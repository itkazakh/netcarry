/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core.util;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * CircularCounter 环形计数器
 * 
 * @author yinyayun
 */
public class CircularCounter {
    private AtomicInteger counter = new AtomicInteger(0);
    private int lower;
    private int upper;

    /**
     * 实际上不会允许到达上限
     * 
     * @param lower
     * @param upper
     */
    public CircularCounter(int lower, int upper) {
        this.lower = lower;
        this.upper = upper;
        this.counter = new AtomicInteger(lower);
    }

    public int count() {
        counter.compareAndSet(upper, lower);
        return counter.getAndIncrement();
    }
}
