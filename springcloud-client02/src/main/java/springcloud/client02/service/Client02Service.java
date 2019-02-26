package springcloud.client02.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import springcloud.client02.hystricfallback.Client02ServiceHystric;

@FeignClient(value = "service-feign", fallback = Client02ServiceHystric.class)
public interface Client02Service {

    @GetMapping("/testzipkin")
    String testZipkin(@RequestParam("client02Port") String client02Port);

}
