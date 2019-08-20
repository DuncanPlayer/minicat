package com.messi.web.minicat.utils;

import io.netty.handler.codec.http.Cookie;
import io.netty.handler.codec.http.CookieDecoder;
import io.netty.handler.codec.http.HttpRequest;
import java.util.Collections;
import java.util.Set;

import static io.netty.handler.codec.http.HttpHeaderNames.COOKIE;

public class CookieUtil {

    public static void setCookie(HttpRequest request,StringBuilder responseContent){
        Set<Cookie> cookies;
        String value = request.headers().get(COOKIE);
        if (value == null) {
            cookies = Collections.emptySet();
        } else {
            cookies = CookieDecoder.decode(value);
        }
        for (Cookie cookie : cookies) {
            responseContent.append("COOKIE: " + cookie.toString() + "\r\n");
        }
    }
}
