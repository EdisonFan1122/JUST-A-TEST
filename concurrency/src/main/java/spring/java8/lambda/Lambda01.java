package spring.java8.lambda;

import spring.java8.lambda.MyFunction;
import spring.java8.lambda.MyFunction2;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

/**
 * Created by Edison on 2018/10/19.
 * -> 匿名函数
 * 左侧参数列表，右侧内容（lambda体），参数列表数据类型可以省略不写，因为编译器可以通过上下文推断出类型
 * 左一遇括号省，右一遇括号遇return省，左侧推断类型省
 * 需要函数式接口的支持，使用 @FunctionalInterface 修饰，只有一个抽象方法时才可通过编译
 */
public class Lambda01 {

    public static void main(String args[]) {
        Lambda01 lambda01 = new Lambda01();
        lambda01.test();
        lambda01.test1();
        lambda01.test2();
        lambda01.test5();
        lambda01.test6();
    }

    List<spring.java8.lambda.Employee> employees = Arrays.asList(new spring.java8.lambda.Employee(1, "2121", 2000D, "男"),
            new spring.java8.lambda.Employee(2, "2122", 3000D, "女"),
            new spring.java8.lambda.Employee(3, "2342", 4000D, "男"),
            new spring.java8.lambda.Employee(4, "2651", 5000D, "女"),
            new spring.java8.lambda.Employee(5, "2343", 6000D, "男"));

    public void test() {
        employees.stream()
                .filter((e) -> e.getSalary() > 2000)
                .filter((e) -> "男".equals(e.getGender()) || "女".equals(e.getGender()))
                .limit(3)
                .forEach(System.out::println);
    }

    /**
     * 无参数无返回值
     */
    public void test1() {

        int num = 9999;
        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("111" + num);
            }
        };

        Runnable r1 = () -> System.out.println("222" + num);

        r.run();
        r1.run();
    }

    /**
     * 一个参数，无返回值
     * 一个参数可以省略括号
     */
    public void test2() {
        Consumer consumer1 = (x) -> System.out.println(x);
        Consumer consumer2 = x -> System.out.println(x);
        Consumer consumer3 = System.out::println;
        consumer1.accept("consumer1");
        consumer2.accept("consumer2");
        consumer3.accept("consumer3");
    }

    /**
     * 有两个以上参数，且lambda体中有多条语句,用大括号
     */
    public void test3() {
        Comparator<Integer> com = (x, y) -> {
            System.out.println("compare");
            return Integer.compare(x, y);
        };
    }

    /**
     * 有两个及以上参数，但lambda体中只有一个语句,大括号和return都可以省略不写
     */
    public void test4() {
        Comparator<Integer> com1 = (x, y) -> Integer.compare(x, y);
        Comparator<Integer> com2 = Integer::compare;
    }

    public void test5() {
        //调用方法
        System.out.println(operate(200L, 300L, (x, y) -> x + y));
    }

    public void test6() {
        String newStr1 = operate2("ewedfwe", String::toUpperCase);
        String newStr2 = operate2("ewedfwe", str -> str.toUpperCase().substring(1, 3));
        System.out.println(newStr1);
        System.out.println(newStr2);
    }

    public Long operate(Long l1, Long l2, MyFunction<Long, Long> myFunction) {
        return myFunction.getValue(l1, l2);
    }

    public String operate2(String str, MyFunction2 myFunction2) {
        return myFunction2.getValue(str);
    }


}
