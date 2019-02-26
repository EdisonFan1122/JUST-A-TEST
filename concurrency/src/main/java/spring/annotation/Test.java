package spring.annotation;

/**
 * Created by Edison on 2018/7/12.
 */
public class Test {


    /**
     * @Deprecated，表示此方法过时或暂时不用
     * @SuppressWarnings("deprecation")表示此方法忽视@Deprecated注解，编译器不会发出警告
     */
    @SuppressWarnings("deprecation")
    public void sing(){
        Person p =new Student();
        p.name();
    }
}
