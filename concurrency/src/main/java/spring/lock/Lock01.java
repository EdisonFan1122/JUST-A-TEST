package spring.lock;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.StampedLock;

/**
 * Created by Edison on 2018/7/21.
 */

@Slf4j
public class Lock01 {

    private static int clientTotal = 5000000;

    private static int threadTotal = 200;

    private static int count = 0;

    //默认使用的是不公平锁false：sync = new ReentrantLock.NonfairSync();
//    private static Lock lock =new ReentrantLock();

    //优化后的读写锁，含有普通的读写锁，还有乐观读
    private static StampedLock lock =new StampedLock();


    public static void main(String[] args) throws Exception{
        long startTime=System.currentTimeMillis();
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        final CountDownLatch countDownLatch = new CountDownLatch(clientTotal);
        for (int i = 0; i < clientTotal; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    add();
                    semaphore.release();
                } catch (Exception e) {
                    log.error("exception", e);
                }
                countDownLatch.countDown();
            });
        }
        countDownLatch.await();
        executorService.shutdown();
        log.info("count:{}",count);
        long endTime=System.currentTimeMillis(); //获取结束时间
        log.info("程序运行时间： "+(endTime-startTime)+"ms");
    }

    private static void add() {
//        lock.lock();
//        try{
//            count++;
//        }finally {
//            lock.unlock();
//        }

        long stamp = lock.writeLock();
        try{
            count++;
        }finally {
            lock.unlock(stamp);
        }
    }

}
