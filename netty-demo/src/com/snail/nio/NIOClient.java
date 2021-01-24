package com.snail.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * @author shenyanjun
 * @create 2021-01-10 8:12 下午
 * @Description TODO
 */
public class NIOClient {
    public static void main(String[] args) throws IOException {
        //获取一个网络通道，并设置非阻塞
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        //连接server
        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 6666);
        //连接服务器
        if (!socketChannel.connect(inetSocketAddress)) {
            while (!socketChannel.finishConnect()) {
                System.out.println("连接服务端失败！");
            }
        }
        String str = "hello server!!";
        ByteBuffer buffer = ByteBuffer.wrap(str.getBytes());
        socketChannel.write(buffer);
        socketChannel.close();


    }
}
