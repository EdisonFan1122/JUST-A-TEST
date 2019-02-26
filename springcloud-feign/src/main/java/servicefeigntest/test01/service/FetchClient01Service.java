package servicefeigntest.test01.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import servicefeigntest.test01.hystricfallback.FetchClient01ServiceHystric;

@FeignClient(value = "service-client01", fallback = FetchClient01ServiceHystric.class)
public interface FetchClient01Service {

    /**
     * 当参数为多个时需要@RequestParam(name="client02Port")指定
     * 当传入参数为对象时@RequestBody User user
     *
     * @param client02Port
     * @param feignPort
     * @return
     */
    @GetMapping("/testzipkin-client01")
    String testZipkin(@RequestParam("client02Port") String client02Port,
                      @RequestParam("feignPort") String feignPort);
}
