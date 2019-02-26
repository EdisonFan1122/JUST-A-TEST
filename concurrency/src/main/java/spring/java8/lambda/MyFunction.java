package spring.java8.lambda;

/**
 * Created by Edison on 2018/10/23.
 */
@FunctionalInterface
public interface MyFunction<T1,T2> {

    public Long getValue(T1 t1,T2 t2);
}
