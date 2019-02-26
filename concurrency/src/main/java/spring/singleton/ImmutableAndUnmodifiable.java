package spring.singleton;

/**
 * Created by Edison on 2018/7/5.
 */

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * 不可变集合：顾名思义就是说集合是不可被修改的。集合的数据项是在创建的时候提供，并且在整个生命周期中都不可改变。
 * 1.Guava的immutable集合
 * 优点：
 *      1.对不可靠的客户代码库来说，它使用安全，可以在未受信任的类库中安全的使用这些对象
 * 　　　2.线程安全的：immutable对象在多线程下安全，没有竞态条件
 * 　　　3.不需要支持可变性, 可以尽量节省空间和时间的开销. 所有的不可变集合实现都比可变集合更加有效的利用内存 (analysis)
 * 　　　4.可以被使用为一个常量，并且期望在未来也是保持不变的
 * Immutable集合使用方法：
 * 　　一个immutable集合可以有以下几种方式来创建：
 * 　    1.用copyOf方法, 譬如, ImmutableSet.copyOf(set)
 *      2.使用of方法，譬如，ImmutableSet.of("a", "b", "c")或者ImmutableMap.of("a", 1, "b", 2)
 *      3.使用Builder类
 * 2.JDK的Collections.unmodifiableXXX(不是真正的不可变集合)，效率低，不安全，不推荐，
 */
public class ImmutableAndUnmodifiable {
    //创建方法2
    private static final ImmutableList<Integer> list = ImmutableList.of(1,2,3,4);
    //创建方法1
    private static final ImmutableSet<Integer> set = ImmutableSet.copyOf(list);
    private static final ImmutableMap<Integer,String> map = ImmutableMap.of(1,"12",2,"23");
    //创建方法3
    private static final ImmutableMap<Integer,String> map2 = ImmutableMap.<Integer,String>builder()
            .put(1,"34").put(2,"565").build();

    public static void main(String[] args) {
        list.add(5);
        set.add(45);
        map.put(6,"56");
        map2.put(34,"4545");
        System.out.println("运行全部报异常！！！！");
    }


}
