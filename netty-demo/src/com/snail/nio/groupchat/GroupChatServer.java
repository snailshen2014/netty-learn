package com.snail.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;

/**
 * @author shenyanjun
 * @create 2021-01-11 7:58 上午
 * @Description TODO
 */
public class GroupChatServer {

    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer() {
        try {
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            //绑定端口
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            //设置非阻塞
            listenChannel.configureBlocking(false);
            //注册到select 客户端连接事件
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void listen() {
        while (true) {
            try {
                int count = selector.select(2000);
                if (count > 0) {//有事件处理
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        SelectionKey selectionKey = iterator.next();
                        if (selectionKey.isAcceptable()) {
                            SocketChannel socketChannel = listenChannel.accept();
                            socketChannel.configureBlocking(false);
                            socketChannel.register(selector,SelectionKey.OP_READ);
                            System.out.println(socketChannel.getRemoteAddress() + " 上线！！！");
                        } else if (selectionKey.isReadable()) {
                            readData(selectionKey);
                        }
                        //当前selection key清除
                        iterator.remove();
                    }

                } else {
                    System.out.println("等待事件发生...");
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void readData(SelectionKey selectionKey) {
        SocketChannel socketChannel = null;
        try {
            socketChannel= (SocketChannel)selectionKey.channel();
            //创建buffer
            ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
            int read = socketChannel.read(byteBuffer);
            if (read > 0) {
                String msg = new String(byteBuffer.array());
                System.out.println("From client data:"+ msg.trim());
                //向其它客户端转发消息
                transfer(msg,socketChannel);
            }

        }catch (Exception e) {
            e.printStackTrace();
            try {
                System.out.println(socketChannel.getRemoteAddress() + " 离线...");
                //取消注册
                selectionKey.cancel();
                socketChannel.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    //转发消息给其它客户端
    private void transfer(String msg,SocketChannel channel) throws IOException {
        System.out.println("transfer message...");
        for ( SelectionKey key : selector.keys()) {
            //通过key 取出对应的socketchannel
            Channel targetChannel = key.channel();
            //排除自己
            if (targetChannel instanceof SocketChannel && targetChannel != channel) {
                SocketChannel dst = (SocketChannel)targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dst.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        new GroupChatServer().listen();
    }
}
