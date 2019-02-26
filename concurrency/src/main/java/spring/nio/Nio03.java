package spring.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.time.LocalDateTime;
import java.util.Iterator;

/**
 * Created by Edison on 2018/10/25.
 */

public class Nio03 {


    /**
     * 非阻塞式
     * SocketChannel
     * ServerSocketChannel
     *
     * DatagramChannel
     *
     */



    public void client() throws IOException {
        //获取通道
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1",9090));

        //切换通道为非阻塞模式
        socketChannel.configureBlocking(false);

        //分配缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);

        //发送数据给服务端
        bb.put(LocalDateTime.now().toString().getBytes());
        bb.flip();
        socketChannel.write(bb);
        bb.clear();

        //关闭通道
        socketChannel.close();
    }

    public void server() throws IOException {
        //获取通道
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        //切换通道为非阻塞模式
        serverSocketChannel.configureBlocking(false);

        //绑定连接
        serverSocketChannel.bind(new InetSocketAddress(9090));

        //获取选择器
        Selector selector = Selector.open();

        //将通道注册在选择器,并且制定监听事件为接收accept
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        //轮询式获得监听器上已经“装备就绪”的事件
        while(selector.select() > 0){
            //获取当前选择器中所有注册的“选择键（已就绪的监听事件）”
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();

            while (iterator.hasNext()){
                //获取就绪的事件
                SelectionKey sk = iterator.next();

                //判断具体类型，做出具体响应----此处为获取新的连接
                if (sk.isAcceptable()){
                    //接收就绪，获取客户端连接
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    //切换连接为非阻塞
                    socketChannel.configureBlocking(false);
                    //
                    socketChannel.register(selector,SelectionKey.OP_READ);

                    //此处为直接从sk中获取
                }else if (sk.isReadable()){
                    //获取已就绪的读取通道
                    SocketChannel socketChannel = (SocketChannel)sk.channel();

                    //读取数据
                    ByteBuffer bb = ByteBuffer.allocate(1024);

                    int len = 0;
                    while ((len = socketChannel.read(bb)) > 0){
                        bb.flip();
                        System.out.println(new String(bb.array(),0,len));
                        bb.clear();
                    }

                }
            }

            //取消选择键SelectionKey
            iterator.remove();
        }
    }


}


