package spring.java8.lambda;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Created by Edison on 2018/10/23.
 * <p>
 * 一、方法引用：若lanmda体中有方法已经实现，则可以使用方法引用。是lambda的另外一种表现形式
 * 1.需要实现的接口中的抽象方法的参数列表与返回值类型要与当前调用此方法的参数列表与返回值类型一致
 * 2.lambda参数列表中第一个参数为方法的调用者，第二个参数为被调用方法的参数。
 * <p>
 * 三种语法
 * <p>
 * 对象::实例方法名
 * <p>
 * 类::静态方法名
 * <p>
 * 类::实例方法名
 * <p>
 *
 * 二、构造器引用
 * 需要调用的构造器参数列表要与函数式接口中抽象方法的参数列表保持一致
 *
 * 三、数组引用
 * Type[]::new
 */
public class Lambda03 {

    public static void main(String[] args) {
        Lambda03 lambda03 = new Lambda03();
        lambda03.test1();
        lambda03.test2();
        lambda03.test6();
    }

    /**
     * 对象名::实例方法名
     */
    public void test1() {
        PrintStream ps = System.out;
        Consumer<String> con1 = x -> ps.println(x);
        con1.accept("werqwe");

        Consumer<String> con2 = ps::println;
        con2.accept("wereeeeqwe");

//        void accept(T t);
//        对比，参数列表与返回值类型一致
//        public void println(String x) {
//            synchronized (this) {
//                print(x);
//                newLine();
//            }
//        }

        Consumer<String> con3 = System.out::println;
        con2.accept("wereeeeqwe");
    }

    /**
     * 对象名::实例方法名
     */
    public void test2() {
        spring.java8.lambda.Employee employee = new spring.java8.lambda.Employee(12, "eee", 1312D, "男");
        Supplier<String> supplier1 = () -> employee.getGender();
        System.out.println(supplier1.get());
//        T get();
//        对比：
//        public String getGender() {
//            return gender;
//        }
        Supplier<String> supplier2 = employee::getGender;
        System.out.println(supplier2.get());
    }

    /**
     * 类名::静态方法名
     */
    public void test3() {
        Comparator<Integer> con1 = (x, y) -> Integer.compare(x, y);
//        Comparator<Integer> con1 = (x,y)
//        对比：
//        public static int compare(int x, int y) {
//            return (x < y) ? -1 : ((x == y) ? 0 : 1);
//        }
        Comparator<Integer> con2 = Integer::compare;
    }

    /**
     * 类名::实例方法名
     */
    public void test4() {
        BiPredicate<String, String> biPredicate1 = (x, y) -> x.equals(y);

//        规则：除了参数列表及返回类型规则之外，lambda参数列表中第一个参数为方法的调用者，第二个参数为被调用方法的参数。
        BiPredicate<String, String> biPredicate2 = String::equals;
    }


    /**
     * 构造器引用，无参
     */
    public void test5() {
        Supplier<spring.java8.lambda.Employee> supplier1 = () -> new spring.java8.lambda.Employee();

        //对应无参构造方法
        Supplier<spring.java8.lambda.Employee> supplier2 = spring.java8.lambda.Employee::new;
    }

    /**
     * 构造器引用，有参,一个或多个
     */
    public void test6() {
        Function<Integer, spring.java8.lambda.Employee> function1 = x -> new spring.java8.lambda.Employee(x);
        Function<Integer, spring.java8.lambda.Employee> function2 = spring.java8.lambda.Employee::new;

        spring.java8.lambda.Employee employee = function2.apply(10000);
        System.out.println(employee);
    }

    /**
     * 数组引用
     */
    public void test7() {
        Function<Integer,int []> function1 = x-> new int[x];
        Function<Integer,int []> function2 = int[]::new;
        final int[] apply = function1.apply(10);
    }

}
