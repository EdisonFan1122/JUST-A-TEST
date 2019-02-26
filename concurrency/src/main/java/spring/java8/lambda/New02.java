package spring.java8.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Edison on 2018/10/19.
 */
public class New02 {


    public static void main(String args[]) {
        New02 n = new New02();
        n.test();
    }

    public void test() {
        List<A> as = Arrays.asList(new A("a", "f"), new A("a", "fdf"), new A("fds", "fdf"));
        List<String> l=as.stream()
                .filter(p -> "a".equals(p.name))
                .map(n -> n.age)
                .collect(Collectors.toList());
        l.forEach(System.out::println);
    }

    class A {

        @Override
        public String toString() {
            return "A{" +
                    "name='" + name + '\'' +
                    ", age='" + age + '\'' +
                    '}';
        }

        private String name;
        private String age;

        public A(String name, String age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }

}
