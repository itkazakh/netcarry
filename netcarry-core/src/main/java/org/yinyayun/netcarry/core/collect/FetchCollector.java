/**
 * Copyright (c) 2017, yayunyin@126.com All Rights Reserved
 */

package org.yinyayun.netcarry.core.collect;

import java.io.File;
import java.util.concurrent.LinkedBlockingQueue;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;

/**
 * FetchCollector 抓取结果收集
 * 
 * @author yinyayun
 */
public class FetchCollector<T> {
    public Logger logger = LoggerFactory.getLogger(FetchCollector.class);
    private LinkedBlockingQueue<T> queue;

    public FetchCollector(int capacity, String savePath) {
        this.queue = new LinkedBlockingQueue<>(capacity);
        File saveFile = new File(savePath);
        // FileUtils.deleteQuietly(saveFile);
        Gson gson = new Gson();
        CollectorAction<T> action = (T x) -> {
            String line = null;
            if (x instanceof String) {
                line = (String) x;
            }
            else {
                line = gson.toJson(x);
            }
            try {
                FileUtils.writeStringToFile(saveFile, line.concat("\n"), "utf-8", true);
            }
            catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        };
        startCollectThread(action);
    }

    public FetchCollector(int capacity, CollectorAction<T> action) {
        this.queue = new LinkedBlockingQueue<>(capacity);
        startCollectThread(action);
    }

    public void add(T t) {
        try {
            queue.put(t);
        }
        catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }

    public T take() {
        try {
            return queue.take();
        }
        catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public void startCollectThread(CollectorAction<T> action) {
        new Thread(() -> {
            while (true) {
                T t = take();
                action.action(t);
            }
        }).start();
    }
}
