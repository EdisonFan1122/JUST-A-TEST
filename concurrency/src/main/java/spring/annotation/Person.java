package spring.annotation;

/**
 * Created by Edison on 2018/7/12.
 */
public interface Person {

    @Deprecated
    String name();

    int age();
}
