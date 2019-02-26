package spring.java8.optionaltest;

/**
 * Created by Edison on 2018/10/24.
 */
public interface MyFunction03 {

    //接口默认方法
    default String name(){
        return "heheh";
    }
}
