package com.snail.netty.http;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

/**
 * @author shenyanjun
 * @create 2021-01-23 12:20 下午
 * @Description TODO
 */
public class HttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            System.out.println("Pipelin hashcode:" + ctx.pipeline().hashCode() +
                    ",handler hashcode:" + this.hashCode());
            HttpRequest httpRequest = (HttpRequest)msg;
            System.out.println("### uri:"+httpRequest.uri());
            if (httpRequest.uri().equalsIgnoreCase("/favicon.ico"))
                return;
            System.out.println("msg类型=" + msg.getClass());
            System.out.println("客户端地址" + ctx.channel().remoteAddress());
            //回复信息给浏览器
            ByteBuf buf = Unpooled.copiedBuffer("Hello 我是服务器", CharsetUtil.UTF_8);
            //构造一个http的响应httpresponse
            DefaultFullHttpResponse response = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, buf);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain;charset=utf-8");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,buf.readableBytes());
//            ctx.channel().writeAndFlush(response);
            ctx.writeAndFlush(response);
        }
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("### chanel active:" + ctx.channel().remoteAddress());
    }
}
