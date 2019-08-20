package com.messi.web.minicat;

import com.messi.web.minicat.netty.MiniCatServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if (event.getApplicationContext().getParent() == null){
            try{
                //启动服务
                MiniCatServer.getInstance().start();
            }catch (Exception e){

            }
        }
    }
}
