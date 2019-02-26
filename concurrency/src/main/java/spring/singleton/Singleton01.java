package spring.singleton;

/**
 * Created by Edison on 2018/7/4.
 */

import lombok.extern.slf4j.Slf4j;

/**
 * 单例模式
 */
public class Singleton01 {

    /**
     * 懒汉模式01，在第一次使用时创建，线程不安全
     */
//    //私有构造函数
//    private Singleton01() {
//
//    }
//    //单例对象
//    private static Singleton01 ins = null;
//    //静态工厂方法
//    public static Singleton01 getIns() {
//        if (ins == null) {
//            ins = new Singleton01();
//        }
//        return ins;
//    }

    /**
     * 懒汉模式02--同步锁synchronized，线程安全,不推荐，因为效率低
     */
//    //私有构造函数
//    private Singleton01() {
//
//    }
//    //单例对象
//    private static Singleton01 ins = null;
//    //静态工厂方法
//    public static synchronized Singleton01 getIns() {
//        if (ins == null) {
//            ins = new Singleton01();
//        }
//        return ins;
//    }

    /**
     * 懒汉模式03--双重检测（同步锁synchronized），线程不安全
     * 原因：创建对象时JVM指令重排序：
     * 1.分配内存空间
     * 2.初始化对象
     * 3.引用指向内存
     * 当执行顺序为132时，线程A执行完13后，ins！=null，此时线程B进入判断ins！=null,
     * 直接返回ins，但是此时实际ins还未初始化，因此线程不安全
     * 解决办法：volatile+双重检测，来禁止指令重排序
     * private volatile static Singleton01 ins = null;
     * volatile使用场景之一，2.状态标识量
     */
    //私有构造函数
//    private Singleton01() {
//
//    }
//
//    //单例对象
//    private volatile static Singleton01 ins = null;
//
//    //静态工厂方法
//    public static Singleton01 getIns() {
//        if (ins == null) {
//            synchronized (Singleton01.class) {
//                if (ins == null) {
//                    ins = new Singleton01();
//                }
//            }
//        }
//        return ins;
//    }

    /**
     * 饿汉模式01，在类装载的时候创建，线程安全
     * 注意或缺点：1.如果私有构造函数中操作过多，造成类加载过慢
     * 2.如果此类在加载后没实际调用的话会造成资源浪费
     */
//    //私有构造函数
//    private Singleton01() {
//    }
//    //单例对象
//    private static Singleton01 ins = new Singleton01();
//    //静态工厂方法
//    public static Singleton01 getIns() {
//        return ins;
//    }

    /**
     * 饿汉模式02--静态块，在类装载的时候创建，线程安全
     * 注意或缺点：1.如果私有构造函数中操作过多，造成类加载过慢
     * 2.如果此类在加载后没实际调用的话会造成资源浪费
     */
    //私有构造函数
//    private Singleton01() {
//
//    }
//    //单例对象
//    private static Singleton01 ins = null;
//
//    static {
//        ins = new Singleton01();
//    }
//
//    //静态工厂方法
//    public static Singleton01 getIns() {
//        return ins;
//    }

    /**
     * 枚举模式，线程安全，且在实际调用时才做最开始初始化，后续使用也可以直接调用，不会造成资源浪费
     */
    //私有构造函数
    private Singleton01() {

    }
    //静态工厂方法
    public static Singleton01 getIns() {
        return Singleton.INS.sin;
    }

    private enum Singleton{
        INS;
        private Singleton01 sin;
        //JVM保证这个方法只被调用一次
        Singleton(){
            sin = new Singleton01();
        }
        private Singleton01 getIns(){
            return sin;
        }
    }


}
