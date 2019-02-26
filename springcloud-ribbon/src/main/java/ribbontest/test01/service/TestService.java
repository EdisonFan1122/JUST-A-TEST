package ribbontest.test01.service;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class TestService {


    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "testError")
    public String test(){
        return restTemplate.getForObject("http://SERVICE-TEST/ping",String.class);
    }

    public String testError(){
        return "SORRY,RIBBON-ERROR!";
    }
}

/**
 * 在test方法上加上@HystrixCommand注解。
 * 该注解对该方法创建了熔断器的功能，并指定了fallbackMethod熔断方法，
 * 熔断方法直接返回了一个字符串，字符串为"sorry,error!"
 *
 *
 * 当 SERVICE-TEST工程不可用的时候，service-ribbon调用 SERVICE-TEST的API接口时，
 * 会执行快速失败，直接返回一组字符串，而不是等待响应超时，这很好的控制了容器的线程阻塞。
 */


/**
 * 断路器：
 *  在微服务架构中，根据业务来拆分成一个个的服务，服务与服务之间可以相互调用（RPC），
 *  在Spring Cloud可以用RestTemplate+Ribbon和Feign来调用。
 *  为了保证其高可用，单个服务通常会集群部署。由于网络原因或者自身的原因，
 *  服务并不能保证100%可用，如果单个服务出现问题，调用这个服务就会出现线程阻塞，
 *  此时若有大量的请求涌入，Servlet容器的线程资源会被消耗完毕，导致服务瘫痪。
 *  服务与服务之间的依赖性，故障会传播，会对整个微服务系统造成灾难性的严重后果，这就是服务故障的“雪崩”效应。
 *
 * Netflix开源了Hystrix组件，实现了断路器模式，SpringCloud对这一组件进行了整合。 在微服务架构中，一个请求需要调用多个服务是非常常见的，
 * 较底层的服务如果出现故障，会导致连锁故障。当对特定的服务的调用的不可用达到一个阀值（Hystric 是5秒20次） 断路器将会被打开。
 * 断路打开后，可用避免连锁故障，fallback方法可以直接返回一个固定值。
 */
