package com.sylg.blog.service.documentation.factory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @program: blog
 * @description: The ThreadFactory of ScheduledThreadPoolExecutor
 * @author: 忆地球往事
 * @create: 2020-04-11 17:30
 **/
public class ScheduledThreadFactory implements ThreadFactory {
    private AtomicInteger threadNo = new AtomicInteger(1);
    private final String nameStart;

    public ScheduledThreadFactory(String poolName) {
        this.nameStart = "[" + poolName + "-";
    }

    @Override
    public Thread newThread(Runnable r) {
        String nameEnd = "]";
        String threadName = this.nameStart + this.threadNo.getAndIncrement() + nameEnd;
        Thread newThread = new Thread(r,threadName);

        newThread.setDaemon(true);

        if (newThread.getPriority() != 5) {
            newThread.setPriority(5);
        }
        return newThread;
    }
}
