package com.snail.nio;

import java.nio.IntBuffer;

/**
 * @author shenyanjun
 * @create 2021-01-06 7:34 上午
 * @Description TODO
 */
public class BasicBuffer {
    public static void main(String[] args) {
        //创建buffer
        IntBuffer intBuffer = IntBuffer.allocate(4);
        //向buffer 中存放数据
        for (int i = 0 ; i < intBuffer.capacity(); i++) {
            intBuffer.put(i*2);
        }
        //read from buffer,flip将buffer读写切换
        intBuffer.flip();
        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }
    }
}
