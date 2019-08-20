package com.messi.web.minicat.inherit;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public interface Parse {
    void parse(String path, StringBuilder responseContent, HttpRequest request, ChannelHandlerContext ctx);
}
