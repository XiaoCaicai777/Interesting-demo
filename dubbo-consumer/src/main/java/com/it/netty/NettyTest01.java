package com.it.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NettyTest01 {
    private static List<SocketChannel> connectList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocketChannel open = ServerSocketChannel.open();
            open.socket().bind(new InetSocketAddress(18001));
            Selector selector = Selector.open();
            open.configureBlocking(false);
            /* 注册接受类型的 */
            open.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端启动成功");
            while (true){
                /* 如果没有i/o事件的发生,将阻塞等待需要处理的事情发生 */
                selector.select();
                /* 获取selector 中注册的全部事件的selectionKey 实例 */
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                // 遍历selectionKey 对事件进行处理
                while (iterator.hasNext()){
                    SelectionKey next = iterator.next();
                    /* 如果是OP_ACCEPT 事件, 则进行获取和事件注册 */
                    if (next.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel)next.channel();
                        SocketChannel accept = channel.accept();
                        accept.configureBlocking(false);
                        /* 这里只注册了读事件, 如果需要给客户端发送数据可以注册写事件 */
                        accept.register(selector,SelectionKey.OP_READ);
                        System.out.println("客户端链接成功 ");
                    }else if(next.isReadable()){ //如果是OP_READ事件, 则进行读取打印
                        SocketChannel channel = (SocketChannel) next.channel();
                        ByteBuffer allocate = ByteBuffer.allocate(100);
                        int read = channel.read(allocate);
                        if(read>0){
                            System.out.println("客户端发送数据 : " + new String(allocate.array()));
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
