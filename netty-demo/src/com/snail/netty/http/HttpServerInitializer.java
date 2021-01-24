package com.snail.netty.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @author shenyanjun
 * @create 2021-01-23 12:15 下午
 * @Description TODO
 */
public class HttpServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        //加入netty HttpServerCodec(http编解码器)
        pipeline.addLast("MyHttpserverCodec",new HttpServerCodec());
        //自定义handler
        pipeline.addLast("UserDefineHandler",new HttpServerHandler());

    }
}
