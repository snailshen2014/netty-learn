# 一、Java BIO基本介绍

传统的java io编程，相关的类和接口在java.io,BIO(blocking I/O),同步阻塞，服务器实现模式为一个连接一个线程，即客户端有连接请求时服务端就需要启动一个线程进行处理，如果这个连接不做任何事情会造成不必要的线程开销，可以通过线程池机制改善。

BIO方式使用与链接数目比较小且固定的架构，这种方式对服务器的资源要求比较高，并发局限于应用中，JDK1.4以前的唯一选择，程序简单易理解。

BIO、NIO、AIO对比表

|          | BIO      | NIO                    | AIO        |
| -------- | -------- | ---------------------- | ---------- |
| IO模型   | 同步阻塞 | 同步非阻塞（多路复用） | 异步非阻塞 |
| 编程难度 | 简单     | 复杂                   | 复杂       |
| 可靠性   | 差       | 好                     | 好         |
| 吞吐量   | 低       | 高                     | 高         |



# 二、Java NIO基本介绍

Java NIO全称 java no-blocking IO,是指JDK提供的全新API,从JDK1.4开始，提供了一系列的改进的输入、输出的新特性，被统称为NIO(即New IO),是同步非阻塞的

NIO相关的类被放在了java.nio包及子包下，并且对原java.io包中的许多类进行了改进。

NIO有三大核心部分，Channel（通道），Buffer(缓冲区)、Selector(选择器)

NIO是面向缓冲区、或者面向快编程的，数据读取到一个它稍后处理的缓存取，需要时可以在缓冲区中前后移动，这就增加了处理过程中的灵活性，使用它可以提供非阻塞的高伸缩网格

Java NIO 的非阻塞模式，使一个线程从某个通道发送请求或者读取数据，但是它仅能得到目前可用的数据，如果目前没有数据可用时，就什么都不会获取，而不是保持线程阻塞，所以知道数据变的可以读之前，该线程可以继续做其它的事情。

# 三、NIO三大核心原理示意图

![nio-core](https://github.com/snailshen2014/netty-learn/blob/main/netty-markdown/nio-core.jpg)	



* 每个channel都会对应一个buffer
* Selector对应一个线程，一个线程对应多个channel(连接)
* 该图反映了有三个channel注册到了该selector
* 程序切换到哪个channel是由事件决定的，Event就是一个重要的概念
* Selector会根据不同的事件，在各个通道上切换
* buffer就是一个内存块
* 数据的读取和写入时通过buffer，这个和BIO不同，BIO中要么是输入流或者是输出流，不能双向，但是NIO的buffer是可以读或者写的，需要flip切换
* channel是双向的

## 3.1 缓冲区（Buffer）

本质上是一个可以读写书的内存块，可以理解成是一个容器对象（含数组），该对象提供了一组方法，可以更轻松的使用内存块，缓冲区对象内置了一些机制，能够跟踪和记录缓冲区的状态变化情况。Channel提供从文件、网络读取数据的通道，但是读取和写入的数据都必须经过Buffer,如果

![buffer](https://github.com/snailshen2014/netty-learn/blob/main/netty-markdown/buffer.jpg)

## 3.2 通道(Channel)

NIO的通道类似于流，但有些区别如下：

* 通道可以同时进行读写，而流只能读或者只能写
* 通道可以实现异步读写数据
* 通道可以从缓冲读数据，也可以写数据到缓冲

![channel-buffer](https://github.com/snailshen2014/netty-learn/blob/main/netty-markdown/channel-buffer.jpg)

BIO中的Stream是单向的，例如FileInputStream对象只能进行行读取数据的操作，而NIO中的通道(Channel)是双向的，可以进行读写操作。

Channel在NIO中是一个接口，常用的Channel类有:FileChannel、DatagramChannel、ServerSocketChannel和SocketChannel

NIO还提供了MappedByteBuffer,可以让文件直接在内存（堆外内存）中映射，减少系统的拷贝，提高效率。

## 3.3 Selector（选择器)

* Java的NIO，用非阻塞的IO方式，可以用一个线程处理多个客户端的连接，就会使用到Selector(选择器)
* Selector能够检测多个注册的通道上是否有事件发生，如果有事件发生，变获取事件然后对每个事件进行相应的处理，这样就可以用一个单线程去管理多个通道，也就是管理多个连接和请求。
* 只有在连接真正有读写事件发生时，才会进行读写，就大大减少了系统的开销，并且不必为每个连接都创建一个线程，不用去维护多个线程，避免了多线程之间的上下文切换

![selector](https://github.com/snailshen2014/netty-learn/blob/main/netty-markdown/selector.jpg)

selector 相关方法

* selector.select()//阻塞
* selector.select(long timeout)//阻塞timeout毫秒后返回
* selector.wakeup()//唤醒selector
* selector.selectNow()//不阻塞，立马返回



NIO非阻塞网络编程原理分析图

selector,selectionKey,serverSocketChannel,SocketChannel

![selector2](https://github.com/snailshen2014/netty-learn/blob/main/netty-markdown/selector2.jpg)



* 当客户端连接时，会通过ServerSocketChannel得到SocketChannel
* SocketChannel会注册到selecotr上，一个selector可以注册多个socketchannel
* 注册后会返回一个SelectionKey,会和该Selector关联
* Selector进行监听，select方法，返回有事件发生的selectionKey
* 通过SelectionKey反向获取SocketChannel,通过channel完成业务处理





