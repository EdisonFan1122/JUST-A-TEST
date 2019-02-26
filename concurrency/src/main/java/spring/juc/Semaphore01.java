package spring.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Created by Edison on 2018/7/16.
 */
@Slf4j
public class Semaphore01 {
    private static int clientTotal = 20;

    private static int threadTotal = 3;

    public static void main(String[] args) throws Exception {
        ExecutorService executorService = Executors.newCachedThreadPool();
        final Semaphore semaphore = new Semaphore(threadTotal);
        for (int i = 0; i < clientTotal; i++) {
            final int num = i;
            executorService.execute(() -> {
                try {
                    /**
                     * semaphore01
                     */
//                    //获得一个许可，如果无可用许可，将一直阻塞等待
//                    semaphore.acquire();
                    //获得指定数目的许可，如果无可用许可，将一直阻塞等待
                    semaphore.acquire(2);
                    add(num);
//                    //释放一个许可
                    semaphore.release(2);

                    /**
                     * semaphore02
                     * 1.tryAcquire():尝试获取一个许可，如果无可用许可，直接返回false，不会阻塞
                     * 2.semaphore.tryAcquire(3):尝试获取3个许可，如果无可用许可，直接返回false，不会阻塞
                     * 3.semaphore.tryAcquire(300,TimeUnit):尝试获取许可,等待，如果没获取则不执行
                     * 。。。。
                     */
//                    if (semaphore.tryAcquire(2)) {
//                        add(num);
////                    //释放指定数目许可
//                        semaphore.release(2);
//                    }
                } catch (Exception e) {
                    log.error("exception", e);
                }
            });
        }
        executorService.shutdown();
        log.info("finish");
    }

    private static void add(int num) throws Exception {
        log.info("{}", num);
        Thread.sleep(1000);
    }
}


