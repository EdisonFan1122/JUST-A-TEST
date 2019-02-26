package spring.atomic;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

/**
 * Created by Edison on 2018/6/30.
 */

@Slf4j
public class Atomic03 {

    /** AtomicLongFieldUpdater
     *  AtomicIntegerFieldUpdater,用于更新实体类某一int型字段
     *  要求：1.字段值用volatile修饰；2.非static字段
     */
    private static AtomicIntegerFieldUpdater<Atomic03> updater
            = AtomicIntegerFieldUpdater.newUpdater(Atomic03.class,"count");

    @Getter
    private volatile int count =100;

    public static void main(String[] args) {
        Atomic03 a =new Atomic03();
        if(updater.compareAndSet(a,100,120)){
            log.info("update success1",a.getCount());
        }
        if(updater.compareAndSet(a,100,120)){
            log.info("update success2",a.getCount());
        }else{
            log.info("update failed",a.getCount());
        }
    }


}
