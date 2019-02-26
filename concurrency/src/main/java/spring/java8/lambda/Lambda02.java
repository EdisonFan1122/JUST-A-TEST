package spring.java8.lambda;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Created by Edison on 2018/10/23.
 * <p>
 * 四大核心函数接口
 * Consumer<T> :消费型接口
 * void accept(T t);
 * <p>
 * Supplier<T> :供给型接口
 * T get();
 * <p>
 * Function<T, R> :函数型接口
 * R apply(T t);
 * <p>
 * Predictate<T> :断言型接口
 * boolean test(T t);
 */
public class Lambda02 {

    public static void main(String[] args) {
        Lambda02 lambda02 = new Lambda02();
        lambda02.test1();
        lambda02.test2();
        lambda02.test3();
        lambda02.test4();
    }

    /**
     * 消费型接口
     *
     * @param money
     * @param consumer
     */
    public void happy(double money, Consumer<Double> consumer) {
        consumer.accept(money);
    }

    public void test1() {
        happy(2000, money -> System.out.println("消费了" + money + "元！"));
    }

    /**
     * 供给型接口
     * 获取指定数量的int随机数
     */
    public List<Integer> getSomeNum(int count, Supplier<Integer> supplier) {
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            list.add(supplier.get());
        }
        return list;
    }

    public void test2() {
        List<Integer> list = getSomeNum(10, () -> (int) (Math.random() * 100));
        list.forEach(System.out::println);
//        for (Integer li: list) {
//            System.out.println(li);
//        }
    }

    /**
     * 函数型接口
     */
    public String handleString(String str, Function<String, String> function) {
        return function.apply(str);
    }

    public void test3() {
        String newStr = handleString("werqwer", String::toUpperCase);
        System.out.println(newStr);
    }

    /**
     * 断言型接口
     */
    public List<String> handleList(List<String> list, Predicate<String> predicate) {
        List<String> myList = new ArrayList<>();
        for (String ml : list
                ) {
            if (predicate.test(ml)) {
                myList.add(ml);
            }
        }
        return myList;
    }

    public void test4() {
        List<String> list = Arrays.asList("dfsa", "qwerqwe", "2323e", "wefqwe", "ok");
        List<String> newList = handleList(list, li -> li.length() > 3);
        newList.forEach(System.out::println);
    }


}
