/**
 * Copyright (c) 2016, yayunyin@126.com All Rights Reserved
 */
package org.yinyayun.netcarry.core.collect;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * PageCommitThread.java
 * 
 * @author yinyayun
 */
public class PageCommitExecutor {
    private ExecutorService executorService;

    // 最大允许提交的数量
    public PageCommitExecutor(int maxCommitSize) {
        executorService = new ThreadPoolExecutor(maxCommitSize / 2 + 1, maxCommitSize, 0, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<Runnable>(1000));
    }

    public void submit(Runnable run) {
    }
}
