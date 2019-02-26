package spring.threadlocal;


/**
 * Created by Edison on 2018/7/6.
 */
public class RequestHolder {
    private final static ThreadLocal<Long> requestHolder=new ThreadLocal<>();

    //请求进入服务器，却没做相关处理时调用
    public static void add(Long id){
        requestHolder.set(id);
    }

    public static Long getId(){
        return requestHolder.get();
    }

    //移除，防止内存泄漏；接口真正处理完之后处理
    public static void remove(){
        requestHolder.remove();
    }

}
