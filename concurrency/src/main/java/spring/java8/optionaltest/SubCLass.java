package spring.java8.optionaltest;

/**
 * Created by Edison on 2018/10/24.
 * <p>
 * 同时继承父类和实现接口有相同的方法，则以继承优先
 * 如果实现多个接口中有相同的默认方法，必须显示实现其中的一个接口默认方法
 */

public class SubCLass implements spring.java8.optionaltest.MyFunction02, spring.java8.optionaltest.MyFunction03 {
    @Override
    public String name() {
//        return MyFunction02.super.name();
        return spring.java8.optionaltest.MyFunction03.super.name();
    }
}


//父类方法继承优先
//public class SubCLass extends TestMyCla implements MyFunction02{
//
//
//}

