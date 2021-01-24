package com.snail.nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @author shenyanjun
 * @create 2021-01-09 5:25 下午
 * @Description TODO
 */
public class NIOFileChannelCopyFileByTransferFrom {
    public static void main(String[] args) throws IOException {
        FileInputStream fileInputStream = new FileInputStream("test.txt");
        FileChannel srcCh = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream("test_copy2.txt");
        FileChannel dstCh = fileOutputStream.getChannel();

        //使用transferForm完成拷贝
        dstCh.transferFrom(srcCh,0,srcCh.size());
        fileInputStream.close();
        fileOutputStream.close();
    }
}
