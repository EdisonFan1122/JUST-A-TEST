package spring.java8.javastream;

import spring.java8.lambda.Employee;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * Created by Edison on 2018/10/23.
 * <p>
 * 对集合的操作的封装
 * <p>
 * 三个操作步骤：创建stream、中间操作、终止操作
 * 多个中间操作可以形成流水线，除非触发终止操作，否则中间操作不做处理，在终止时一次全部处理，称为惰性求值
 */
public class Stream01 {

    public static void main(String[] args) {
        Stream01 stream01 = new Stream01();
        stream01.test1();
        stream01.test2();
        stream01.test3();
        stream01.test4();
    }

    /**
     * 创建Stream
     */
    public void test1() {

        //1.通过集合提供的stream() 或parallelStream()
        List<String> list = new ArrayList<>();
        Stream<String> stream1 = list.stream();

        //2.通过Arrays中的静态方法stream()获取数组流
        Employee[] employee = new Employee[10];
        Stream<Employee> stream2 = Arrays.stream(employee);

        //3.通过Stream中的静态方法of()
        Stream<String> stream3 = Stream.of("23", "ewe", "ewrw", "23e");

        //4.创建无限流
        //迭代
        Stream<Integer> stream4 = Stream.iterate(0, x -> x + 3);
        stream4.limit(10)
                .forEach(System.out::println);

        //生成
        Stream<Double> stream5 = Stream.generate(Math::random);
        stream5.limit(10)
                .forEach(System.out::println);
    }

    List<Employee> employees = Arrays.asList(new Employee(1, "2121", 2000D, "男"),
            new Employee(2, "2122", 3000D, "女"),
            new Employee(3, "2342", 4000D, "男"),
            new Employee(4, "2651", 5000D, "女"),
            new Employee(3, "2342", 4000D, "男"),
            new Employee(4, "2651", 5000D, "女"),
            new Employee(3, "2342", 4000D, "男"),
            new Employee(4, "2651", 5000D, "女"),
            new Employee(5, "2343", 6000D, "男"));

    /**
     * 中间操作
     * 内部迭代，迭代操作由Stream API完成
     * 筛选与切片
     * filter--接收lambda，从流中排除某些元素
     * limit --截断流，使其元素不超过给定数量
     * skip(n) -- 跳过元素，返回一个去除前n个元素的流，若元素不足n个，则返回一个空值，与limit互补
     * distinct--筛选，通过流所生成元素的hashcode(),equals()去除重复元素
     */
    public void test2() {
        employees.stream()
                .distinct()//要重写hashcode和equals方法
                .filter((e) -> e.getSalary() > 3000)
                .limit(4)
                .skip(1)
                .forEach(System.out::println);
    }


    /**
     * 映射：map--接收Lambda，将元素转换成其他形式或提取信息，接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射到一个新的元素上
     * flatMap--接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有的流连接成一个流
     */
    public void test3() {
        employees.stream()
                .map(Employee::getName)
                .forEach(System.out::println);

        List<String> list = Arrays.asList("1234", "fsdf", "sedwe", "23ew");
        list.stream()
                .map(String::toUpperCase)
                .forEach(System.out::println);

        list.stream()
                .flatMap(Stream01::chaHandle)
                .forEach(System.out::println);

    }

    public static Stream<Character> chaHandle(String str) {
        List<Character> list = new ArrayList<>();
        char[] characters = str.toCharArray();

        for (Character ch : characters) {
            list.add(ch);
        }
        return list.stream();
    }

    /**
     * 排序
     * sorted()--自然排序(Comparable)
     * sortsd()--Comprator com --定制排序，根据类中重写的compareTo方法
     */
    public void test4() {
        List<String> list = Arrays.asList("1234", "fsdf", "sedwe", "23ew");
        list.stream()
                .sorted()
                .forEach(System.out::println);

        employees.stream()
                .sorted((x, y) -> {
                    if (x.getSalary().equals(y.getSalary())) {
                        return x.getName().compareTo(y.getName());
                    } else {
//                        return x.getSalary().compareTo(y.getSalary());
                        //倒序
                        return -x.getSalary().compareTo(y.getSalary());
                    }
                }).forEach(System.out::println);
    }


}
