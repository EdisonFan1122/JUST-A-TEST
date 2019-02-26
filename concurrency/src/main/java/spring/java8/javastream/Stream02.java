package spring.java8.javastream;

import spring.java8.lambda.Employee;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by Edison on 2018/10/23.
 * <p>
 * 查找与匹配
 * allMatch anyMatch NoneMatch findFirst findAny count max min
 * <p>
 * 规约
 * reduce--按照一定规则，将流中的元素反复结合起来，得到一个值  先map-reduce ,先提取，再计算
 */
public class Stream02 {


    public static void main(String[] args) {
        Stream02 stream02 = new Stream02();
        stream02.test1();
        stream02.test2();
        stream02.test3();
        stream02.test4();
    }

    final List<Employee> employees = Arrays.asList(new Employee(1, "2121", 2000D, "男"),
            new Employee(2, "2122", 3000D, "女", Employee.Status.BUSY),
            new Employee(3, "2342", 4000D, "男", Employee.Status.FREE),
            new Employee(4, "2651", 5000D, "女", Employee.Status.VOCATION),
            new Employee(3, "2342", 4000D, "男", Employee.Status.BUSY),
            new Employee(4, "2651", 5000D, "女", Employee.Status.VOCATION),
            new Employee(3, "2342", 4000D, "男", Employee.Status.FREE),
            new Employee(4, "2651", 5000D, "女", Employee.Status.BUSY),
            new Employee(5, "2343", 6000D, "男", Employee.Status.BUSY));


    /**
     * 查找与匹配
     */
    public void test1() {
//        boolean b1 = employees.stream()
//                .allMatch(x -> x.getStatus().equals(Employee.Status.BUSY));
//        System.out.println(b1);
//
//        boolean b2 = employees.stream()
//                .anyMatch(x -> x.getStatus().equals(Employee.Status.BUSY));
//        System.out.println(b2);
//
//        boolean b3 = employees.stream()
//                .noneMatch(x -> x.getStatus().equals(Employee.Status.BUSY));
//        System.out.println(b3);

        Optional<Employee> optional = employees.stream()
                .findFirst();
        System.out.println(optional.get());

        Optional<Employee> optional2 = employees.stream()
                .findAny();
        System.out.println(optional.get());


        long count = employees.stream()
                .count();
        System.out.println(count);

        Optional<Double> op2 = employees.stream()
                .map(Employee::getSalary)
                .max(Double::compare);
        System.out.println(op2.get());

        Optional<Double> op3 = employees.stream()
                .map(Employee::getSalary)
                .min(Double::compare);
        System.out.println(op3.get());
    }

    /**
     * 规约
     */
    public void test2() {
        List<Integer> list = Arrays.asList(1, 2, 3, 45, 6245, 23, 451, 54534);

        Integer sum = list.stream()
                .reduce(0, (x, y) -> x + y);
        System.out.println(sum);

        //因为此处计算无初始值，所以有可能出现为空，因此放入容器Optional,而上面计算有初始值为0
        Optional<Double> op1 = employees.stream()
                .map(Employee::getSalary)
                .reduce(Double::sum);
        System.out.println(op1.get());
    }

    /**
     * 搜集 --collect--将流转换为其他形式，接收一个Collector接口的实现，用于给Stream中的元素做汇总的方法
     * .collect(Collectors.toSet());
     * .collect(Collectors.toCollection(LinkedList::new));对于没有给定的集合，通过toCollection()
     */
    public void test3() {
        Set<String> set = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.toSet());
        System.out.println(set);

        LinkedList<Double> ll = employees.stream()
                .map(Employee::getSalary)
                .collect(Collectors.toCollection(LinkedList::new));
        System.out.println(ll);

        //总数....
        Long count = employees.stream()
                .collect(Collectors.counting());
//                .count();
        System.out.println(count);

        //平均数....
        Double avg = employees.stream()
                .collect(Collectors.averagingDouble(Employee::getSalary));
        System.out.println(avg);

        //最大值  //最小值
        Optional<Employee> op1 = employees.stream()
                .max(Comparator.comparingDouble(Employee::getSalary));
//        .collect(Collectors.maxBy((x, y) -> Double.compare(x.getSalary(), y.getSalary())));
        System.out.println(op1.get());
    }

    /**
     * 分组groupingBy
     * <p>
     * 分区partitioningBy
     */
    public void test4() {
        //一级分组
        Map<Employee.Status, List<Employee>> map1 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus));

        //多级分组
        Map<Employee.Status, Map<String, List<Employee>>> map2 = employees.stream()
                .collect(Collectors.groupingBy(Employee::getStatus, Collectors.groupingBy(e -> {
                    if (e.getSalary() > 4000) {
                        return "还行";
                    } else {
                        return "一般";
                    }
                })));
    }

    /**
     * join
     */
    public void test5() {
        String str = employees.stream()
                .map(Employee::getName)
                .collect(Collectors.joining());
//                 .collect(Collectors.joining(","));//中间加字符分割
//         .collect(Collectors.joining(",","===","!!!"));//中间加字符分割，首尾加其他字符
        System.out.println(str);
    }


}
