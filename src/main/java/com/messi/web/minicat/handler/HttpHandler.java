package com.messi.web.minicat.handler;

import com.messi.web.minicat.constant.ContentType;
import com.messi.web.minicat.constant.PictureAndFileType;
import com.messi.web.minicat.context.MappingContext;
import com.messi.web.minicat.inherit.DealMethod;
import com.messi.web.minicat.inherit.Parse;
import com.messi.web.minicat.inherit.ReadSources;
import com.messi.web.minicat.method.DealWithGet;
import com.messi.web.minicat.method.DealwithPost;
import com.messi.web.minicat.parse.HtmlParse;
import com.messi.web.minicat.parse.IndexParse;
import com.messi.web.minicat.reader.CSSReader;
import com.messi.web.minicat.reader.HtmlReader;
import com.messi.web.minicat.reader.JSReader;
import com.messi.web.minicat.reader.PictureReader;
import com.messi.web.minicat.reflection.ReflectMethod;
import com.messi.web.minicat.utils.GetURLParams;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import java.net.URI;

import static io.netty.buffer.Unpooled.copiedBuffer;
import static io.netty.handler.codec.http.HttpHeaderNames.CONTENT_TYPE;
import static io.netty.handler.codec.rtsp.RtspHeaderNames.CONTENT_LENGTH;

public class HttpHandler extends SimpleChannelInboundHandler<HttpObject> {

    private HttpRequest request;

    private StringBuilder responseContent = new StringBuilder();

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject httpObject) throws Exception {
        messageReceived(ctx,httpObject);
    }

    private void messageReceived(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest request = this.request = (HttpRequest) msg;
            URI uri = new URI(request.getUri());
            String path= uri.getPath();
            System.out.println("request uri==" + path);
            if (path.equals("/")) {
                IndexParse.index(responseContent,ctx);
                return;
            }else if (path.contains(PictureAndFileType.HTML) || path.contains(PictureAndFileType.JS) || path.contains(PictureAndFileType.CSS)){
                Parse parse = new HtmlParse();
                parse.parse(path,responseContent,request,ctx);
            }else if(path.contains(PictureAndFileType.PNG) || path.contains(PictureAndFileType.JPG) || path.contains(PictureAndFileType.JPEG) || path.contains(PictureAndFileType.ICO)){
                String pictureLocation = MappingContext.take("pictureLocation");
                pictureLocation = pictureLocation + path;
                PictureReader.getPictureAndWrite(pictureLocation,request,ctx.channel(),ContentType.IMAGE);
            }else if (!path.contains(PictureAndFileType.HTML) && request.getMethod().equals(HttpMethod.GET)){
                //GET处理用户请求数据，并返回JSON数据
                responseContent.setLength(0);
                Object[] params = GetURLParams.getUrlparams(request.getUri());
                String result = ReflectMethod.reflectMethod(MappingContext.take(path),params);
                responseContent.append(result);
                DealMethod get = new DealWithGet();
                get.deal(request, responseContent, ctx,ContentType.TEXTPLAIN);
                return;
            }else if(request.getMethod().equals(HttpMethod.POST)){
                DealMethod post = new DealwithPost(msg);
                post.deal(request,responseContent,ctx,ContentType.TEXTHTML);
                return;
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.channel().close();
    }
}
