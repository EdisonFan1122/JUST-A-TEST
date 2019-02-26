package spring.juc;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Created by Edison on 2018/8/1.
 */
@Slf4j
public class FutureTask01 {
    public static void main(String[] args) throws Exception{
        FutureTask<String> ft =new FutureTask<String>(new Callable<String>() {
            @Override
            public String call() throws Exception {
                log.info("do sth in Callable");
                Thread.sleep(5000);
                return "done!";
            }
        });

        new Thread(ft).start();
        log.info("do sth in main");
        Thread.sleep(1000);
        String result = ft.get();
        log.info("done!");

    }
}
