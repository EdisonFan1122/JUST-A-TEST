package spring.java8.optionaltest;

import spring.java8.lambda.Employee;

import java.util.Optional;

/**
 * Created by Edison on 2018/10/24.
 */
public class Optional01 {

    public static void main(String[] args) {
        Optional01 optional01 = new Optional01();
        optional01.test1();
    }

    public void test1(){
        Optional<Employee> op1 = Optional.of(new Employee());
        System.out.println(op1.get());

        //此处空指针异常
//        Optional<Employee> op2 = Optional.of(null);
//        System.out.println(op2.get());

//        Optional<Employee> op3 = Optional.empty();
//        //此处get空指针异常
//        System.out.println(op3.get());

//        Optional<Employee> op4 = Optional.ofNullable(null);
//        //此处get空指针异常
//        System.out.println(op4.get());

        Optional<Employee> op5 = Optional.ofNullable(null);
        //如果没有则用orelse中的替代
        Employee employee = op5.orElse(new Employee(12,"123",21342D,"男",Employee.Status.FREE));
        System.out.println(employee);

        //使用orElseGet获取S获取的值  orElseGet(Supplier s)
        Employee employee2 = op5.orElseGet(Employee::new);
        System.out.println(employee2);

          //此处getname为空值，空指针异常
//        Optional<String> op6 = op5.map(Employee::getName);
          //get空指针异常
//        System.out.println(op6.get());

        Optional<String> op7 = op5.flatMap(e->Optional.of(e.getName()));
        //get空指针异常
        System.out.println(op7.get());
    }

}
