package com.messi.web.minicat.method;

import com.messi.web.minicat.inherit.DealMethod;
import com.messi.web.minicat.inherit.Response;
import com.messi.web.minicat.response.TextHtmlResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class DealWithGet implements DealMethod {

    @Override
    public void deal(HttpRequest request, StringBuilder responseContent,ChannelHandlerContext ctx,String type) {
        // GET Method
        Response response = new TextHtmlResponse();
        response.writeResponse(ctx.channel(),responseContent,request,type);
    }
}
