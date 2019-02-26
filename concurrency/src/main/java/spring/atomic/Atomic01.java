package spring.atomic;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * Created by Edison on 2018/6/26.
 */

@Slf4j
public class Atomic01 {

    private static int clientTotal = 5000;

    private static int threadTotal = 200;

    /**
     * 1.CAS：
     * AtomicInteger的原理是依靠底层的cas来保障原子性的更新数据，在要添加或者减少的时候，
     *  会使用死循环不断地cas到特定的值，从而达到更新数据的目的。
     */

    /**
     *  2.LongAdder在AtomicLong区别：
     *  LongAdder在AtomicLong的基础上将单点的更新压力分散到各个节点，
     *  在低并发的时候通过对base的直接更新可以很好的保障和AtomicLong的性能基本保持一致，
     *  而在高并发的时候通过分散提高了性能。
     *  缺点是LongAdder在统计的时候如果有并发更新，可能导致统计的数据有误差。
     */


    /**
     * 2.AtomicDouble和AtomicLong：64位，分字节
     */


    private static AtomicInteger count = new AtomicInteger(0);

    private static AtomicLong count1 = new AtomicLong(0);

    private static LongAdder count2 = new LongAdder();

    public static void main(String[] args) throws Exception {
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
        log.info("count:{}", count);
        log.info("count1:{}", count1);
        log.info("count2:{}", count2);

    }

    private static void add() {
        count.incrementAndGet();
        count1.incrementAndGet();
        count2.increment();
    }
}
