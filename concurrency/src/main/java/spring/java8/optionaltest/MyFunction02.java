package spring.java8.optionaltest;

/**
 * Created by Edison on 2018/10/24.
 *
 * 接口中接口允许有静态方法
 */
public interface MyFunction02 {

    //接口默认方法
    default String name(){
        return "wewe";
    }

    public static void show(){
        System.out.println("23232");
    }
}
