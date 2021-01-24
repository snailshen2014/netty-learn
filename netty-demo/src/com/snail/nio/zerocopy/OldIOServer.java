package com.snail.nio.zerocopy;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author shenyanjun
 * @create 2021-01-17 11:44 上午
 * @Description 传统io 服务器
 */
public class OldIOServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(7001);
        while (true) {
            Socket socket = serverSocket.accept();
            DataInputStream dataInputStream = new DataInputStream(socket.getInputStream());
            try {
                byte[] bytes = new byte[4096];
                while (true) {
                    int readCount = dataInputStream.read(bytes,0,bytes.length);
//                    System.out.println("Read data from client:"+ new String(bytes));
                    if ( -1 == readCount) break;
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
