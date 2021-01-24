package com.snail.nio.zerocopy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.nio.channels.FileChannel;
import java.nio.channels.SocketChannel;

/**
 * @author shenyanjun
 * @create 2021-01-17 12:05 下午
 * @Description TODO
 */
public class NewIOClient {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.connect(new InetSocketAddress("localhost",7001));
        String fileName = "/Users/yanjunshen/Downloads/Notion.dmg";
        FileChannel fileChannel = new FileInputStream(fileName).getChannel();
        long start = System.currentTimeMillis();
        long transferTo = fileChannel.transferTo(0, fileChannel.size(), socketChannel);
        System.out.println("发送的字节数="+ transferTo + ",耗时:"+(System.currentTimeMillis()-start));
        fileChannel.close();


    }
}
