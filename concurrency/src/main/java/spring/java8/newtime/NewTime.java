package spring.java8.newtime;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by Edison on 2018/10/24.
 */
public class NewTime {
    public static void main(String[] args) throws Exception{
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        ExecutorService pool = Executors.newFixedThreadPool(10);

        Callable<LocalDate> task = () -> LocalDate.parse("20170909", dateTimeFormatter);

        List<Future<LocalDate>> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            list.add(pool.submit(task));
        }

        for (int i = 0; i < 1000; i++){
            System.out.println(list.get(i).get());
        }

        pool.shutdown();
    }

}
