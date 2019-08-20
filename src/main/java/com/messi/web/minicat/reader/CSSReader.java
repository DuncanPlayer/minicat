package com.messi.web.minicat.reader;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class CSSReader {

    public static String cssRead(String path, HttpRequest request, ChannelHandlerContext ctx, String type){
        StringBuilder builder = new StringBuilder();
        builder.append("<style>");
        File file = null;
        BufferedReader cssReader = null;
        try{
            file = new File(path);
            cssReader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = cssReader.readLine()) != null){
                builder.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                cssReader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        builder.append("</style>");
        return builder.toString();
    }
}
