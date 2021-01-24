package com.snail.nio;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author shenyanjun
 * @create 2021-01-09 4:16 下午
 * @Description TODO
 */
public class NIOFileChannel {
    public static void main(String[] args) throws IOException {
        String str = "hello snail!";
        //创建输出流
        FileOutputStream fileOutputStream = new FileOutputStream("./test.txt");
        //获取文件channel
        FileChannel fileChannel = fileOutputStream.getChannel();
        //创建一个缓冲区bytebuffer
        ByteBuffer buffer =  ByteBuffer.allocate(128);
        buffer.put(str.getBytes());
        buffer.flip();
        fileChannel.write(buffer);

    }
}
