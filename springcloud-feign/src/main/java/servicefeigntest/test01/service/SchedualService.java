package servicefeigntest.test01.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import servicefeigntest.test01.hystricfallback.SchedualServiceHystric;


/**
 * 定义一个feign接口，通过@ FeignClient（“服务名”），来指定调用哪个服务。比如在代码中调用了service-test服务的“/test”接口定义一个feign接口，
 * 通过@ FeignClient（“服务名”），来指定调用哪个服务。比如在代码中调用了service-hi服务的“/hi”接口
 */


/**
 *  Feign是自带断路器的，在D版本的Spring Cloud之后，它没有默认打开。需要在配置文件中配置打开它，在配置文件加以下代码：
 *  feign.hystrix.enabled=true
 *  基于service-feign工程进行改造，只需要在FeignClient的SchedualService接口的注解中加上fallback的指定类就行了：
 *  SchedualServiceHystric需要实现SchedualService 接口，并注入到Ioc容器中.
 */
@FeignClient(value = "service-client02",fallback = SchedualServiceHystric.class)
public interface SchedualService {

    @GetMapping("/ping")
    String ping();
}
