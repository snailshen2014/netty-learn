package com.snail.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author shenyanjun
 * @create 2021-01-09 4:30 下午
 * @Description TODO
 */
public class NIOFileChannelCopyFile {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("test.txt");
        FileChannel channel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("test_copy.txt");
        FileChannel dst = fileOutputStream.getChannel();
        ByteBuffer byteBuffer = ByteBuffer.allocate(10);

        int readed = 0;
        while ((readed = channel.read(byteBuffer)) != -1) {
            System.out.println("### readed =" + readed);
            byteBuffer.flip();
            dst.write(byteBuffer);
            byteBuffer.clear();
        }
        fileInputStream.close();
        fileOutputStream.close();


    }
}
