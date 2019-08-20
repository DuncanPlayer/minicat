package com.messi.web.minicat.thread;

import com.messi.web.minicat.utils.SqlConnection;

import java.sql.Connection;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadPoolExecutor;

public class GetDBConnection {

    private static ThreadPoolExecutor dbPool = DBThreadPool.getInstance();

    public static Connection getDbConnection() {
        Callable<Connection> callable = new Callable<Connection>() {
            @Override
            public Connection call() throws Exception {
                //获取一个数据库连接
                return SqlConnection.getInstance();
            }
        };

        FutureTask<Connection> futureTask = new FutureTask<>(callable);

        dbPool.execute(futureTask);

        try{
            //等待一秒钟
            //Thread.sleep(1000);
            return futureTask.get();
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

}
