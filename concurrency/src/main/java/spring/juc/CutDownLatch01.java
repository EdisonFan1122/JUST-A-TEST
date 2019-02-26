package spring.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by Edison on 2018/7/11.
 */
@Slf4j
public class CutDownLatch01 {

    private static int threadTotal = 20;

    private static int count = 0;

    public static void main(String[] args) throws Exception {
//        ExecutorService executorService = Executors.newCachedThreadPool();
//        final CountDownLatch countDownLatch = new CountDownLatch(threadTotal);
//        for (int i = 0; i < threadTotal; i++) {
//            final int num = i;
//            executorService.execute(() -> {
//                try {
//                    add(num);
//                } catch (Exception e) {
//                    log.error("exception", e);
//                }finally {
//                    countDownLatch.countDown();
//                }
//            });
//        }
//        countDownLatch.await(3, TimeUnit.MILLISECONDS);
//        log.info("finish");
//        executorService.shutdown();


        ExecutorService executorService = Executors.newCachedThreadPool();
        while (threadTotal >= 0) {
            for (int i = 0; i < threadTotal; i++) {
                int num = i;
                executorService.execute(() -> {
                    try {
                        add(num);
                    } catch (Exception e) {
                        log.error("exception", e);
                    } finally {
                        threadTotal--;
                    }
                });
            }
        }


        log.info("finish");
        executorService.shutdown();


    }

    private static void add(int num) throws Exception {
        log.info("{}", num);
        Thread.sleep(1000);
    }
}
