package spring.sync;

import lombok.extern.slf4j.Slf4j;

/**
 * Created by Edison on 2018/6/30.
 */
@Slf4j
public class Sync01 {

    /**
     * 修饰一个代码块
     * 作用于调用代码块的对象
     */
    public void test2() {
        synchronized (this) {
            for (int i = 0; i < 10; i++) {
                log.info("test1", i);
            }
        }
    }

    /**
     * 修饰一个方法
     * 作用于调用该代码的对象
     */
    public synchronized void test3() {
        for (int i = 0; i < 10; i++) {
            log.info("test1", i);
        }
    }

    /**
     * 修饰一个静态方法
     * 作用于所有对象
     */
    public static synchronized void test4() {
        for (int i = 0; i < 10; i++) {
            log.info("test1", i);
        }
    }

    /**
     * 修饰一个类
     * 作用于类的所有对象
     */
    public static void test1() {
        synchronized (Sync01.class) {
            for (int i = 0; i < 10; i++) {
                log.info("test1", i);
            }
        }
    }


    public static void main(String[] args) {

    }
}
