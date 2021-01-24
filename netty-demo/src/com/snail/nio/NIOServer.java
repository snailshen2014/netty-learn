package com.snail.nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @author shenyanjun
 * @create 2021-01-10 7:53 下午
 * @Description TODO
 */
public class NIOServer {
    public static void main(String[] args) throws IOException {
        //创建ServerSocketChannel ->ServerSocket
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        //得到一个selector
        Selector selector = Selector.open();
        //绑定端口
        serverSocketChannel.socket().bind(new InetSocketAddress(6666));
        //设置非阻塞
        serverSocketChannel.configureBlocking(false);

        //把serverSocketChannel OP_ACCEPT事件注册到selector
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //等待客户端连接
        while (true) {
            //等待1s没有事件发生
            if (selector.select(1000) == 0) {
                System.out.println("服务器等待1s,无连接请求！！！");
                continue;
            }
            //获取事件集合
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {//遍历事件集合
                SelectionKey selectionKey = iterator.next();
                try {
                    if (selectionKey.isAcceptable()) {//连接事件
                        System.out.println("new connection...");
                        //获取连接的socket channel
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        socketChannel.configureBlocking(false);
                        //注册到selector ,侦听读事件,并关联buffer
                        socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(512));

                    } else if (selectionKey.isReadable()) {//读事件
                        //通过selectionkey 获取channel
                        SocketChannel channel = (SocketChannel) selectionKey.channel();
                        //获取channel关联的buffer
                        ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                        channel.read(buffer);//读数据
                        System.out.println("Reading from client data:" + new String(buffer.array()));
//                        buffer.clear();
                    } else if (selectionKey.isValid() && selectionKey.isWritable()) {//客户端可写
                        System.out.println("write client...");
                    }


                } catch (Exception e) {
                    //IO异常，客户端断开连接
                    e.printStackTrace();
                    iterator.remove();
                }
                //手动从集合中移除selectionKey,防止重复操作
                iterator.remove();
            }

        }
    }
}
