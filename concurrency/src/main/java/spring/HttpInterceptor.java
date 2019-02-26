package spring;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import spring.threadlocal.RequestHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Edison on 2018/7/6.
 */
@Slf4j
public class HttpInterceptor extends HandlerInterceptorAdapter {
    //请求之前做处理
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        return super.preHandle(request, response, handler);
//        log.info("preHandle");
        return true;
    }

    //请求之后，正常请求处理
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        super.postHandle(request, response, handler, modelAndView);
//    }

    //有异常处理；正常请求处理后也会进入此方法
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
//        super.afterCompletion(request, response, handler, ex);
//        log.info("afterCompletion");
        RequestHolder.remove();
        return;
    }
}
