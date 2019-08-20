package com.messi.web.minicat.response;

import com.messi.web.minicat.inherit.Response;
import com.messi.web.minicat.utils.CookieUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONNECTION;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONTENT_LENGTH;

public class TextHtmlResponse implements Response {

    @Override
    public void writeResponse(Channel channel, StringBuilder responseContent, HttpRequest request,String type) {
        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);

        boolean close = request.headers().contains(CONNECTION, HttpHeaders.Values.CLOSE, true)
                || request.getProtocolVersion().equals(HttpVersion.HTTP_1_1)
                && !request.headers().contains(CONNECTION, HttpHeaders.Values.KEEP_ALIVE, true);

        FullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
        response.headers().set(CONTENT_TYPE, type);

        if (!close) {
            response.headers().set(CONTENT_LENGTH, buf.readableBytes());
        }

        //获取并设置cookie
        CookieUtil.setCookie(request,responseContent);

        ChannelFuture future = channel.writeAndFlush(response);
        if (close) {
            future.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
