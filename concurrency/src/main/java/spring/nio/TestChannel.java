package spring.nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * Created by Edison on 2018/10/24.
 *
 * 1.通道（Channel）:用于源节点与目标节点的连接。在java NIO中负责缓冲区中数据的传输，Channel本身不存储数据，因此需要配合缓冲区进行传输
 *
 * 2.主要实现类：
 *      1.java.nio.channels.Channel接口
 *          FileChannel     --文件通道
 *          SocketChannel  -- TCP
 *          ServerSocketChannel  --TCP
 *          DatagramChannel   --TCP
 *
 * 3.获取：
 *      1.本地IO：
 *          FileInputStream\FileOutputStream
 *          RandomAccessFile
 *
 *      网络IO
 *          Socket \ServerSocket \ DatagramSocket
 *
 *      2.NIO.2中针对各个通道提供了静态方法open()
 *      3.NIO.2的Files工具类的newByteChannel()
 *
 *  4.通道之间的数据传输--推荐使用，使用直接缓冲区完成文件的复制，内存映射文件
 *      transferForm()
 *      transferTo()
 *
 *   5.分散与聚集
 *      分散读取（Scattering Reads) :将通道中的数据分散到多个缓冲区
 *      聚集写入（Gathering Writes）:将多个缓冲区中的数据聚集到通道中
 *
 *   6.字符集：Charset   char 与 byte之间的解码与编码
 *      编码：字符串--字节数组
 *      解码：字节数组--字符串
 *
 *
 */
public class TestChannel {

    public static void main(String[] args) throws Exception {
        TestChannel testChannel = new TestChannel();
        testChannel.test();
        testChannel.test2();
        testChannel.test3();
        testChannel.test5();
    }

    public void test5() throws Exception{
        Charset cs1 = Charset.forName("UTF-8");

        //获取编码器
        CharsetEncoder ce = cs1.newEncoder();
        CharsetDecoder cd = cs1.newDecoder();

        CharBuffer cb = CharBuffer.allocate(111);
        cb.put("色访问额外！");
        cb.flip();

        //编码
        ByteBuffer bb = ce.encode(cb);

        for (int i = 0;i < 12;i++){
            System.out.println(bb.get());
        }

        cb.flip();
        CharBuffer cb2 = cd.decode(bb);
        System.out.println(cb2.toString());

    }

    /**
     * 分散与聚集
     *      分散读取（Scattering Reads) :将通道中的数据分散到多个缓冲区
     *      聚集写入（Gathering Writes）:将多个缓冲区中的数据聚集到通道中
     * @throws Exception
     */
    public void test4() throws Exception{
        RandomAccessFile ra = new RandomAccessFile("1.txt","rw");

        //获取通道
        FileChannel channel1 = ra.getChannel();

        //分配指定大小的缓冲区
        ByteBuffer bu1 = ByteBuffer.allocate(222);
        ByteBuffer bu2 = ByteBuffer.allocate(1024);

        //分散读取
        ByteBuffer [] bbs ={bu1,bu2};
        channel1.read(bbs);

        //打印
        System.out.println(new String(bbs[0].array(),0,bbs[0].limit()));
        System.out.println("-------------------------------------------");
        System.out.println(new String(bbs[1].array(),0,bbs[1].limit()));

        //统一装换为写
        for (ByteBuffer b :bbs) {
            b.flip();
        }

        //聚集写入
        RandomAccessFile ra2 = new RandomAccessFile("2.txt","rw");
        FileChannel fileChannel2 = ra2.getChannel();
        fileChannel2.write(bbs);
    }

    /**
     * 通道之间的数据传输--推荐使用，使用直接缓冲区完成文件的复制，内存映射文件
     *      transferForm()
     *      transferTo()
     * @throws Exception
     */
    public void test3() throws Exception{
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"),StandardOpenOption.READ);
        FileChannel outChannel = FileChannel.open(Paths.get("4.png"),StandardOpenOption.CREATE,StandardOpenOption.READ,StandardOpenOption.WRITE);

        inChannel.transferTo(0,inChannel.size(),outChannel);
        inChannel.close();

//        outChannel.transferFrom(inChannel,0,inChannel.size());
//        outChannel.size();
    }

    /**
     * 使用直接缓冲区完成文件的复制，内存映射文件
     *
     * 读写的IN OUT 是针对应用程序而言，
     * 1.程序将 外面的文件1.png 读进程序内存  ，通过channel形成内存映射文件
     * 2.程序将
     *
     */
    public void test2() throws Exception {
        //读进来通道
        FileChannel inChannel = FileChannel.open(Paths.get("1.png"), StandardOpenOption.READ);
        //写出去通道
        FileChannel outChannel = FileChannel.open(Paths.get("3.png"), StandardOpenOption.READ, StandardOpenOption.WRITE,StandardOpenOption.CREATE);

        //内存映射文件
        MappedByteBuffer inmbb = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
        MappedByteBuffer outmbb = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

        //直接对缓冲区进行数据的读写操作
        byte[] bytes = new byte[inmbb.limit()];
        inmbb.get(bytes);
        outmbb.put(bytes);

        inChannel.close();
        outChannel.close();
    }

    /**
     * 利用通道完成文件的复制，非直接缓冲区
     *
     * @throws Exception
     */
    public void test() throws Exception {
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("1.png");
            fos = new FileOutputStream("2.png");

            //创建通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //分配缓冲区
            ByteBuffer bb = ByteBuffer.allocate(1024);

            //将通道中的数据写入缓冲区
            //  先判断缓冲区为空的
            while (inChannel.read(bb) != -1) {
                bb.flip();//装换为写的模式
                outChannel.write(bb);
                bb.clear();//清空缓冲区，继续写
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            //通道和流关闭
            inChannel.close();
            outChannel.close();
            fis.close();
            fos.close();
        }
    }


}
