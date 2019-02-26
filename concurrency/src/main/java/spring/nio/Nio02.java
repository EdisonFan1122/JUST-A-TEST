package spring.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Edison on 2018/10/25.
 *
 * 1.通道：负责连接
 *      java.nio.channels.Channel接口
 *          SelectableChannel
 *              SocketChannel
 *              ServerSocketChannel
 *              DatagramChanel
 *
 *              Pipe.SinkChannel
 *              Pipe.SourceChannel
 * 2.缓冲区：负责数据的存取
 *
 * 3.选择器(Selector):是SelectableChannel的多路复用器，用于监控SelectableChannel的IO状况
 */
public class Nio02 {


    /**
     * 阻塞式
     */

    public void client() throws Exception{
        //获取通道
        SocketChannel sc = SocketChannel.open(new InetSocketAddress("127.0.0.1",9898));

        //获取文件读磁盘通道in  ,read
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);

        //分配指定大小的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);

        //读取本地文件发送到服务端,不等于-1则表示读到了
        while (inChannel.read(bb) != -1){
            bb.flip();
            sc.write(bb);
            bb.clear();
        }

        sc.shutdownOutput();

        //接收服务端的反馈
        int len = 0;
        while ((len = sc.read(bb)) != -1){
            bb.flip();
            System.out.println(new String(bb.array(),0,len));
            bb.clear();
        }

        //关闭通道
        sc.close();
        inChannel.close();
    }

    public void server() throws Exception{
        //获取通道
        ServerSocketChannel ssc = ServerSocketChannel.open();

        //获取文件写磁盘通道out  ,write
        FileChannel outChannel = FileChannel.open(Paths.get("3.png"),StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        //获取客户端连接的通道
        SocketChannel sc = ssc.accept();

        //绑定连接
        ssc.bind(new InetSocketAddress(9898));

        //分配指定大小的缓冲区
        ByteBuffer bb = ByteBuffer.allocate(1024);

        //接收客户端文件并写入本地磁盘
        while(outChannel.read(bb)!=-1){
            bb.flip();
            outChannel.write(bb);
            bb.clear();
        }

        //发送反馈给客户端
        bb.put("服务端接收数据成功！".getBytes());
        bb.flip();
        sc.write(bb);

        //关闭通道
        sc.close();
        outChannel.close();
        ssc.close();
    }
}
