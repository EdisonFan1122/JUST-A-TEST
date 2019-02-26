package spring.java8.newtime;


import java.lang.reflect.Method;
import java.time.*;

/**
 * Created by Edison on 2018/10/24.
 * <p>
 * Duration--计算时间间隔
 * Period--计算日期间隔
 */
public class NewTime02 {
    public static void main(String[] args) throws Exception{

        //人可读的
        LocalTime time = LocalTime.now();
        System.out.println(time);

        LocalDateTime ldt = LocalDateTime.of(2018, 2, 3, 12, 23, 12);
        System.out.println(ldt);

        LocalDateTime ldt2 = ldt.plusHours(10);
        System.out.println(ldt2);

        //机器可读  默认格林威职时间
        Instant instant1 = Instant.now();
        System.out.println(instant1);

        //偏移时间
        OffsetDateTime odt = instant1.atOffset(ZoneOffset.ofHours(8));
        System.out.println(odt);

        Instant instant3 = Instant.ofEpochSecond(60);
        System.out.println(instant3);

        test();
    }

    public static void test() throws NoSuchMethodException {
        Class<NewTime02> clazz = NewTime02.class;
        Method m1 = clazz.getMethod("show");
        System.out.println(m1);
        System.out.println(m1.getTypeParameters());
        System.out.println(m1.getReturnType());
    }

    public void show(String aaa) {

    }


}
