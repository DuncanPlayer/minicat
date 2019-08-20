package com.messi.web.minicat.reader;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.rtsp.RtspHeaderNames;
import io.netty.util.CharsetUtil;

import java.io.File;
import java.io.FileInputStream;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_LENGTH;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONNECTION;

public class PictureReader {

    public static void getPictureAndWrite(String path, HttpRequest request, Channel channel, String type){
        //获取图片
        FileInputStream fis =null;
        byte[] data = {};
        long size = 0;
        try{
            File file = new File(path);
            fis = new FileInputStream(file);
            size = file.length();
            byte[] temp = new byte[(int)size];
            fis.read(temp,0,(int)size);
            data = temp;

            if (data.length > 0){
                //byte[]  图片
                ByteBuf buf = copiedBuffer(data);
                boolean close = request.headers().contains(CONNECTION, HttpHeaders.Values.CLOSE, true)
                        || request.getProtocolVersion().equals(HttpVersion.HTTP_1_1)
                        && !request.headers().contains(CONNECTION, HttpHeaders.Values.KEEP_ALIVE, true);
                FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
                response.headers().set(CONTENT_TYPE, type);
                response.headers().set(CONTENT_LENGTH,size);

                if (!close) {
                    response.headers().set(RtspHeaderNames.CONTENT_LENGTH, buf.readableBytes());
                }

                ChannelFuture future = channel.writeAndFlush(response);
                if (close) {
                    future.addListener(ChannelFutureListener.CLOSE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                fis.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}
