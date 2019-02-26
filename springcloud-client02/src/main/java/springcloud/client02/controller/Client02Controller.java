package springcloud.client02.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springcloud.client02.service.Client02Service;

@RestController
@ResponseBody
public class Client02Controller {

    /**
     *
     */

    @Autowired
    private Client02Service client02Service;

    @Value("${server.port}")
    String client02Port;

    @GetMapping("/ping")
    public String test() {
        return "PONG,i am from port:" + client02Port;
    }

    @GetMapping("/testzipkin")
    public String testZipkin() {
        return client02Service.testZipkin(client02Port);
    }

}
