package com.it.netty;

import org.springframework.util.CollectionUtils;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketOption;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NettyTest {
    private static List<SocketChannel> connectList = new ArrayList<>();

    public static void main(String[] args) {
        try {
            ServerSocketChannel open = ServerSocketChannel.open();
            open.socket().bind(new InetSocketAddress(18000));
            /* 配置不阻塞 */
            open.configureBlocking(false);
            System.out.println("服务端启动成功....");
            while (true){
                SocketChannel accept = open.accept();
                if (accept!=null) {
                    System.out.println("客户端已建立链接....");
                    /* 将客户端的链接也配置未非阻塞的 */
                    accept.configureBlocking(false);
                    connectList.add(accept);
                }
                Iterator<SocketChannel> iterator = connectList.iterator();
                if(iterator.hasNext()){
                    SocketChannel next = iterator.next();
                    ByteBuffer allocate = ByteBuffer.allocate(100);
                    int read = next.read(allocate);
                    if(read>0 ){
                        System.out.println("客户端发送的数据: " + new String(allocate.array()));
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
