package com.messi.web.minicat.method;

import com.messi.web.minicat.inherit.DealMethod;
import com.messi.web.minicat.inherit.Response;
import com.messi.web.minicat.response.TextHtmlResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.multipart.*;

import java.io.IOException;

public class DealwithPost implements DealMethod {

    private boolean readingChunks;

    private static final HttpDataFactory factory = new DefaultHttpDataFactory(DefaultHttpDataFactory.MINSIZE);

    private HttpPostRequestDecoder decoder;

    private HttpRequest request;
    private StringBuilder responseContent;
    private  HttpObject msg;

    public DealwithPost(HttpObject msg){
        this.msg = msg;
    }


    @Override
    public void deal(HttpRequest request, StringBuilder responseContent, ChannelHandlerContext ctx,String type) {
        this.request = request;
        this.responseContent = responseContent;
        try {
            /**
             * 通过HttpDataFactory和request构造解码器
             */
            decoder = new HttpPostRequestDecoder(factory, request);
        } catch (HttpPostRequestDecoder.ErrorDataDecoderException e1) {
            e1.printStackTrace();
            responseContent.append(e1.getMessage());
            Response response = new TextHtmlResponse();
            response.writeResponse(ctx.channel(),responseContent,request,type);
            ctx.channel().close();
            return;
        }

        readingChunks = HttpHeaders.isTransferEncodingChunked(request);
        responseContent.append("Is Chunked: " + readingChunks + "\r\n");
        responseContent.append("IsMultipart: " + decoder.isMultipart() + "\r\n");
        if (readingChunks) {
            // Chunk version
            responseContent.append("Chunks: ");
            readingChunks = true;
        }
        if (decoder != null) {
            if (msg instanceof HttpContent) {
                // New chunk is received
                HttpContent chunk = (HttpContent) msg;
                try {
                    decoder.offer(chunk);
                } catch (Exception e1) {
                    e1.printStackTrace();
                    responseContent.append(e1.getMessage());
                    Response response = new TextHtmlResponse();
                    response.writeResponse(ctx.channel(),responseContent,request,type);
                    ctx.channel().close();
                    return;
                }
                responseContent.append('o');
                try {
                    while (decoder.hasNext()) {
                        InterfaceHttpData data = decoder.next();
                        if (data != null) {
                            try {
                                writeHttpData(data);
                            } finally {
                                data.release();
                            }
                        }
                    }
                } catch (HttpPostRequestDecoder.EndOfDataDecoderException e1) {
                    responseContent.append("\r\n\r\nEND OF CONTENT CHUNK BY CHUNK\r\n\r\n");
                }

                // example of reading only if at the end
                if (chunk instanceof LastHttpContent) {
                    Response response = new TextHtmlResponse();
                    response.writeResponse(ctx.channel(),responseContent,request,type);
                    readingChunks = false;
                    reset();
                }
            }
        }
    }

    private void reset() {
        request = null;
        decoder.destroy();
        decoder = null;
    }


    private void writeHttpData(InterfaceHttpData data) {
        /**
         * HttpDataType有三种类型
         * Attribute, FileUpload, InternalAttribute
         */
        if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
            Attribute attribute = (Attribute) data;
            String value;
            try {
                value = attribute.getValue();
            } catch (IOException e1) {
                e1.printStackTrace();
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType().name() + ":"
                        + attribute.getName() + " Error while reading value: " + e1.getMessage() + "\r\n");
                return;
            }
            if (value.length() > 100) {
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType().name() + ":"
                        + attribute.getName() + " data too long\r\n");
            } else {
                responseContent.append("\r\nBODY Attribute: " + attribute.getHttpDataType().name() + ":"
                        + attribute.toString() + "\r\n");
            }
        }
    }
}
