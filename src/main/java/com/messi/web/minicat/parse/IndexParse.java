package com.messi.web.minicat.parse;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.CharsetUtil;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONTENT_LENGTH;

public class IndexParse {

    public static void index(StringBuilder responseContent,ChannelHandlerContext ctx){
        responseContent.setLength(0);
        responseContent.append("<html>");
        responseContent.append("<head>");
        responseContent.append("<title>MiniCat</title>\r\n");
        responseContent.append("</head>\r\n");
        responseContent.append("<body bgcolor=white><style>td{font-size: 12pt;}</style>");
        responseContent.append("<center><h2>Welcome to MiniCat</h2></center>");
        responseContent.append("<center><h4>minicat with netty,just like tomcat a little</h4></center>");
        responseContent.append("<center><h4>yet Users can request data asynchronously through ajax and encapsulated the JDBC</h4></center>");
        responseContent.append("</body>");
        responseContent.append("</html>");

        ByteBuf buf = copiedBuffer(responseContent.toString(), CharsetUtil.UTF_8);
        FullHttpResponse response = new DefaultFullHttpResponse(
                HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);

        response.headers().set(CONTENT_TYPE, "text/html; charset=UTF-8");
        response.headers().set(CONTENT_LENGTH, buf.readableBytes());
        ctx.channel().writeAndFlush(response);
    }
}
