package servicefeigntest.test01.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import servicefeigntest.test01.service.FetchClient01Service;
import servicefeigntest.test01.service.SchedualService;


/**
 * 在Web层的controller层，对外暴露一个"/ping"的API接口，
 * 通过上面定义的Feign客户端SchedualService 来消费服务
 */
@RestController
@ResponseBody
public class SchedualController {

    /**
     * 编译器报错，无视。 因为这个Bean是在程序启动的时候注入的，编译器感知不到，所以报错。
     */
    @Autowired
    private SchedualService schedualService;

    @Autowired
    private FetchClient01Service fetchClient01Service;

    @Value("${server.port}")
    String feignPort;

    @GetMapping("/ping")
    public String ping() {
        return schedualService.ping();
    }

    @GetMapping("/testzipkin")
    public String testZipkin(@RequestParam("client02Port") String client02Port) {
        return fetchClient01Service.testZipkin(client02Port, feignPort);
    }
}
