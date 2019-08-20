package com.messi.web.minicat.parse;

import com.messi.web.minicat.constant.ContentType;
import com.messi.web.minicat.constant.PictureAndFileType;
import com.messi.web.minicat.context.MappingContext;
import com.messi.web.minicat.inherit.DealMethod;
import com.messi.web.minicat.inherit.Parse;
import com.messi.web.minicat.inherit.ReadSources;
import com.messi.web.minicat.method.DealWithGet;
import com.messi.web.minicat.reader.CSSReader;
import com.messi.web.minicat.reader.HtmlReader;
import com.messi.web.minicat.reader.JSReader;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public class HtmlParse implements Parse {


    @Override
    public void parse(String path, StringBuilder responseContent, HttpRequest request, ChannelHandlerContext ctx){
        //js和css文件内容添加html尾部
        if (responseContent != null && responseContent.length() > 0 && path.contains(PictureAndFileType.JS)){
            String resourcesPath = MappingContext.take("resoucespath");
            resourcesPath = resourcesPath + path;
            String jsContent = JSReader.jsRead(resourcesPath, request, ctx, ContentType.TEXTPLAIN);
            responseContent.append(jsContent);
        }else if (responseContent != null && responseContent.length() > 0 && path.contains(PictureAndFileType.CSS)){
            String resourcesPath = MappingContext.take("resoucespath");
            resourcesPath = resourcesPath + path;
            String cssContent = CSSReader.cssRead(resourcesPath, request, ctx, ContentType.TEXTPLAIN);
            responseContent.append(cssContent);
        }
        String resourcesPath = MappingContext.take("resoucespath");
        resourcesPath = resourcesPath + path;
        ReadSources readSources = new HtmlReader();
        responseContent = readSources.read(resourcesPath);
        DealMethod get = new DealWithGet();
        get.deal(request, responseContent, ctx,ContentType.TEXTHTML);
    }


}
