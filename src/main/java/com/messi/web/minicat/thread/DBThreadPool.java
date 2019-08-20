package com.messi.web.minicat.thread;

import java.util.concurrent.*;

public class DBThreadPool {

    public static class Singleton{
        private static ThreadPoolExecutor dbPool;
        static {
            //keepAliveTime:这个就是线程空闲时可以存活的时间，一旦超过这个时间，线程就会被销毁。
            dbPool = new ThreadPoolExecutor(5, Runtime.getRuntime().availableProcessors() * 2, 60, TimeUnit.SECONDS,
                    new ArrayBlockingQueue<>(200),
                    new ThreadFactory() {
                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread(r);
                            t.setName("DBConnection_Thread");
                            if (t.isDaemon()) {
                                t.setDaemon(false);
                            }
                            if (Thread.NORM_PRIORITY == t.getPriority()) {
                                t.setPriority(Thread.NORM_PRIORITY);
                            }
                            return t;
                        }
                    }, new RejectedExecutionHandler() {
                @Override
                public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {
                    System.out.println("拒绝策略RejectedExecutionHandler");
                }
            });
        }
        public static ThreadPoolExecutor getInstance(){
            return dbPool;
        }
    }

    public static ThreadPoolExecutor getInstance(){
        return Singleton.getInstance();
    }
}
