package com.messi.web.minicat.reader;

import com.messi.web.minicat.inherit.DealMethod;
import com.messi.web.minicat.method.DealWithGet;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class JSReader {

    public static String jsRead(String path, HttpRequest request, ChannelHandlerContext ctx,String type){
        StringBuilder builder = new StringBuilder();
        builder.append("<script>");
        File file = null;
        BufferedReader reader = null;
        file = new File(path);
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            String line = null;
            while ((line = reader.readLine()) != null){
                builder.append(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try {
                reader.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        builder.append("</script>");
        return builder.toString();
    }
}
