package com.messi.web.minicat.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class MiniCatServer {

    private static Logger LOGGER = LoggerFactory.getLogger(MiniCatServer.class);

    //主线程组
    private EventLoopGroup masterGroup;
    //从线程组
    private EventLoopGroup slaveGroup;

    //启动类
    private ServerBootstrap serverBootstrap;
    private ChannelFuture future;

    public MiniCatServer() {
        masterGroup = new NioEventLoopGroup();
        slaveGroup = new NioEventLoopGroup();
        serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(masterGroup,slaveGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new MiniCatInitialzer());
    }

    public void start(){
        this.future = serverBootstrap.bind(9099);
        LOGGER.info("MINICAT START UP ON {}",9099);
    }

    private static class Singleton{
        private static  MiniCatServer miniCatServer;
        static {
            miniCatServer = new MiniCatServer();
        }
        public static MiniCatServer getInstance(){
            return miniCatServer;
        }
    }

    public static MiniCatServer getInstance(){
        return Singleton.getInstance();
    }
}
