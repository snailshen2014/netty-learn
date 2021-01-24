package com.snail.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * @author shenyanjun
 * @create 2021-01-09 8:13 下午
 * @Description TODO
 */
public class ScatteringAndGatheringTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        InetSocketAddress inetSocketAddress = new InetSocketAddress(7000);
        serverSocketChannel.socket().bind(inetSocketAddress);

        //创建buffer数组
        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        int messageLength = 8;

        SocketChannel socketChannel = serverSocketChannel.accept();
        while (true) {
            int byteReaded = 0;
            while (byteReaded < messageLength) {
                long read = socketChannel.read(byteBuffers);
                byteReaded += read;//累计读取的字节数
                System.out.println("byte readed=" + byteReaded);

                //输出当前的byte buffers
                Arrays.asList(byteBuffers).stream().map(buffer -> "postion=" +
                        buffer.position() + ",limit=" + buffer.limit()).forEach(System.out::println);

                //将所有的buffer进行flip
                Arrays.asList(byteBuffers).forEach(buffer -> buffer.flip());
                //将数据读出显示到客户端
                long bytewrite = 0;
                while (bytewrite < messageLength) {
                    long l = socketChannel.write(byteBuffers);
                    bytewrite+= l;
                }
                //将所有buffer进行clear
                Arrays.asList(byteBuffers).forEach(buffer-> buffer.clear());
            }
        }

    }
}
