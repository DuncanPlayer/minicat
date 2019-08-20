package com.messi.web.minicat.inherit;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.HttpRequest;

public interface Response {

    void writeResponse(Channel channel, StringBuilder responseContent, HttpRequest request,String type);
}
