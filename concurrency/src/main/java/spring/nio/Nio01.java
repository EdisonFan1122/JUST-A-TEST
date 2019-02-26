package spring.nio;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Edison on 2018/10/24.
 * <p>
 * IO--面向流(Stream)，字节流动，且单向传输。类比于水
 * NIO--面向缓冲区（Buffer载体、存储），双向传输。通道类比于铁路，只能连接不能运输；缓冲区类比于火车，可以运输，且双向.
 * 通道用于打开到IO设备（文件或套接字（网络））的连接，使用NIO时，需要获取用于连接IO设备的通道以及容纳数据的缓冲区，然后操作缓冲区，对数据进行处理，
 * 简而言之，Channel负责传输，Buffer负责存储
 * <p>
 * 针对网络传输时，IO为阻塞塞式，NIO为非阻塞式，且有选择器（Selectors）
 * <p>
 * <p>
 * <p>
 * <p>
 * 一、缓冲区--在javaNIO中负责数据的存取，缓冲区就是数组，用于存储不同的数据类型的数据
 * ByteBuffer\CharBuffer..... boolean除外，其他7种基本数据类型都有相应缓冲区，通过allocate()获取缓冲区
 * <p>
 * 二、缓冲区方法
 * put()：存入缓冲区的数据
 * get():获取缓冲区的数据
 * <p>
 * 三、缓冲区核心属性
 * capacity:总大小
 * limit:允许最大的操作位置，limit之后的不允许读写
 * position:位置，当前提取位置
 * mark:标记，表示记录当前position的位置，可以通过reset恢复到mark的位置
 * <p>
 * 0<=mark<=position<=limit<=capacity
 *
 * 四、非直接缓冲区与直接缓冲区
 * 非直接缓冲区：通过allocate()分配，建立在JVM内存中  (磁盘--内核地址空间--copy--用户地址空间--应用程序),来回复制效率低
 *
 * 直接缓冲区：通过allocateDirect()分配，建立在物理内存中，可以提高效率    (磁盘--物理内存映射文件--应用程序),效率高
 */
public class Nio01 {

    public static void main(String[] args) {
        Nio01 nio01 = new Nio01();
//        nio01.test1();
//        nio01.test2();
//        nio01.test3();
        String key = "sddfsf";
        String value = "sdfs";
        Map map = new HashMap();
        map.put(null,null);

        Map conmap = new ConcurrentHashMap();
        conmap.put(null,"rsdf");

        Map map2 = Collections.EMPTY_MAP;
        map2.put("wsef","wer");

        Map tmap = new Hashtable();
        tmap.put(null,null);
        System.out.println(key.hashCode());

    }

    public void test1() {
        //1.分配指定大小的缓冲区,字节
        ByteBuffer bb = ByteBuffer.allocate(1024);
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //2.写
        String str1 = "232";
        bb.putInt(12343234);//int占4字节
        bb.put(str1.getBytes());
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //3.转
        bb.flip();//通过此方法，将put模式转换为get模式，position归0，limit为实际占用量，capacity不变
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //4.读
        byte[] bytes = new byte[bb.limit()];
        bb.get(bytes);
        System.out.println(new String(bytes, 0, bytes.length));
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());

        //5.rewind--可重复读，position回到起点
        bb.rewind();

        //6.clear--清空缓冲区(limit回归到最大值)，但是缓冲区中的数据依然存在，处于“被遗忘”状态
        bb.clear();
        System.out.println(bb.position());
        System.out.println(bb.limit());
        System.out.println(bb.capacity());
    }

    public void test2() {
        ByteBuffer bb = ByteBuffer.allocate(1024);

        String str = "1234123";
        bb.put(str.getBytes());

        bb.flip();

        byte[] bytes1 = new byte[bb.limit()];
        bb.get(bytes1, 0, 3);
        //offset表示从第几位开始往byte数组放，lenth表示取几次get()
//        public ByteBuffer get(byte[] dst, int offset, int length) {
//            checkBounds(offset, length, dst.length);
//            if (length > remaining())
//                throw new BufferUnderflowException();
//            int end = offset + length;
//            for (int i = offset; i < end; i++)
//                dst[i] = get();
//            return this;
//        }

        System.out.println("读取到的数据是：" + new String(bytes1));
        System.out.println("1.取3位后，当前position位置为：" + bb.position());

        bb.mark();//标记当前position位置为3
        System.out.println("2.标记，当前position位置为：" + bb.position());

        byte[] bytes2 = new byte[bb.limit()];
        //再继续取2位，position为5
        bb.get(bytes2, 3, 2);
        System.out.println("读取到的数据是：" + new String(bytes2));
        System.out.println("3.再取两位，当前position位置为：" + bb.position());

        bb.reset();//通过reset 回到mark标记的位置
        System.out.println("4.reset后，当前position位置为：" + bb.position());

        byte[] bytes3 = new byte[bb.limit()];
        bb.get(bytes3, 3, 2);
        System.out.println("读取到的数据是：" + new String(bytes3));
        System.out.println("5.再取两位，当前position位置为：" + bb.position());


        //查看缓冲区是否还有，如果有则返回剩下的数量
        if (bb.hasRemaining()) {
            System.out.println("当前剩余数量为：" + bb.remaining());
        }
    }

    /**
     * 创建直接缓冲区allocateDirect
     */
    public void test3(){
        ByteBuffer bb = ByteBuffer.allocateDirect(1024);
        //判断是否为直接缓冲区
        System.out.println(bb.isDirect());
    }

}
