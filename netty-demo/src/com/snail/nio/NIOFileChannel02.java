package com.snail.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author shenyanjun
 * @create 2021-01-09 4:25 下午
 * @Description TODO
 */
public class NIOFileChannel02 {
    public static void main(String[] args) throws IOException {
        //创建输入流
        File file = new File("./test.txt");
        FileInputStream fileInputStream = new FileInputStream(file);
        FileChannel fileChannel = fileInputStream.getChannel();
        ByteBuffer buffer = ByteBuffer.allocate((int)file.length());
        fileChannel.read(buffer);
        System.out.println("Reading conetent from file:" + new String(buffer.array()));
    }
}
