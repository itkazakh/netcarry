/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core;

import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * FetchCollector 抓取结果收集
 * 
 * @author yinyayun
 */
public class FetchCollector<T> {
    public Logger logger = LoggerFactory.getLogger(FetchCollector.class);
    private LinkedBlockingQueue<T> queue;

    public FetchCollector(int capacity) {
        queue = new LinkedBlockingQueue<>(capacity);
    }

    public void collector(T t) {
        try {
            queue.put(t);
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public T take() throws InterruptedException {
        return queue.take();
    }
}
