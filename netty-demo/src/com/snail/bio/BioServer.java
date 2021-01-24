package com.snail.bio;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author shenyanjun
 * @create 2021-01-05 9:15 下午
 * @Description bio socket server通过telnet 127.0.0.1 6666 测试
 */
public class BioServer {
    private static final int PORT = 6666;

    public static void main(String[] args) throws IOException {

        ExecutorService executorService = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(PORT);
        System.out.println("服务器启动了...");
        while (true) {
            System.out.println("server accept block....");
            //阻塞点
            final Socket socket = serverSocket.accept();
            System.out.println("server accept block end ,a client connect...");
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    System.out.println("A new connection thread id:"+Thread.currentThread().getId() + ",socket id:"+socket);
                    handleRequest(socket);
                }
            });
        }
    }

    private static void handleRequest(Socket socket) {
        byte[] bytes = new byte[1024];
        int readed = 0;
        try (InputStream inputStream = socket.getInputStream()) {
            //read阻塞
            System.out.println("read block....");
            while ((readed = inputStream.read(bytes)) != -1) {
                System.out.println("read block end...,data coming...");
                System.out.println( new String(bytes,0,readed));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
