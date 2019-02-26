package spring.java8.javastream;

import java.time.Duration;
import java.time.Instant;
import java.util.stream.LongStream;

/**
 * Created by Edison on 2018/10/24.
 *
 * 并行流
 */
public class Stream03 {


    public static void main(String[] args) {
        Stream03 stream03 = new Stream03();
//        stream03.test1();//9300
        stream03.test2();
    }
    public void test1(){
        Instant begin = Instant.now();
        LongStream.rangeClosed(0,10000000000L)
                .parallel()
                .reduce(Long::sum);
        Instant end = Instant.now();

        System.out.println(Duration.between(begin,end).toMillis());
    }

    public void test2(){
        Instant begin = Instant.now();
        Long sum = 0L;
        for(int i =0;i<=10000000000L;i++){
            sum+=i;
        }
        Instant end = Instant.now();

        System.out.println(Duration.between(begin,end).toMillis());
    }
}
