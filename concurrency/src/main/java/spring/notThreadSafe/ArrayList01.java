package spring.notThreadSafe;


import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Edison on 2018/7/7.
 */
@Slf4j
public class ArrayList01 {
    private static int clientTotal = 5000;

    private static int threadTotal = 200;


    /**
     * 1.线程不安全  ArrayList         HashSet  HashMap
     * 同步容器，不一定所有环境线程安全，且效率低，不作推荐使用
     * 对容器中遍历时，最好不要做移除操作，应当使用for循环，或者标记要移除的元素，遍历完之后再移除。
     * 2.对应安全的为Vector(实现List接口)         HashTable(实现Map接口)  Stack
     *  用法相同，都为synchronized修饰同步方法
     * 3.Collections.synchronizedXXX(List,Set,Map)
     */
//    private static List<Integer> list = new ArrayList<>();
//    private static List<Integer> vector = new Vector<>();
//    private static Map<Integer,Integer> table = new Hashtable<>();

    //同步容器......
    private static List<Integer> list = Collections.synchronizedList(new ArrayList<>());


    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int count = i;
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add(count);
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("{}", list.size());
    }

    private static void add(int i) {
        list.add(i);
    }


}
