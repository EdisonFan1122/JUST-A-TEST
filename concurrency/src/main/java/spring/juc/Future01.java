package spring.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

/**
 * Created by Edison on 2018/8/1.
 */

@Slf4j
public class Future01 {

    static class MyCallable implements Callable<String> {

        @Override
        public String call() throws Exception {
            log.info("do sth in Callable");
            Thread.sleep(5000);
            return "done!";
        }
    }

    public static void main(String[] args) throws Exception{
        ExecutorService es = Executors.newCachedThreadPool();
        Future<String> future = es.submit(new MyCallable());
        log.info("do sth in main");
        String result = future.get();
        Thread.sleep(1000);
        log.info(result);

    }
}
