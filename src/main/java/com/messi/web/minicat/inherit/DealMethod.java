package com.messi.web.minicat.inherit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public interface DealMethod {

    void deal(HttpRequest request,StringBuilder responseContent, ChannelHandlerContext ctx,String type);
}
