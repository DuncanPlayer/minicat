package com.messi.web.minicat.netty;

import com.messi.web.minicat.context.MappingContext;
import com.messi.web.minicat.handler.HttpHandler;
import com.messi.web.minicat.utils.LoadProperties;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.*;
import java.util.Iterator;
import java.util.Properties;

public class MiniCatInitialzer extends ChannelInitializer<SocketChannel> {

    private static Properties mappingProperties;

    static {
        mappingProperties = LoadProperties.loadProperties(LoadProperties.MAPPINGURL);
        Iterator<Object> itr = mappingProperties.keySet().iterator();
        while (itr.hasNext()){
            String key =  itr.next().toString();
            String value = mappingProperties.getProperty(key);
            MappingContext.put(key,value);
        }
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        //http服务器端对request解码
        pipeline.addLast("decoder", new HttpRequestDecoder());
        //http服务器端对response编码
        pipeline.addLast("encoder", new HttpResponseEncoder());
        //压缩
        pipeline.addLast("deflater", new HttpContentCompressor());
        //增加心跳机制
        //pipeline.addLast(new IdleStateHandler(8,10,12));
        //pipeline.addLast(new HeartBeatHandler());

        pipeline.addLast(new HttpHandler());

    }
}
