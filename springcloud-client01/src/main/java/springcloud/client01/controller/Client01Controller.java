package springcloud.client01.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ResponseBody
public class Client01Controller {

    @Value("${server.port}")
    String client01port;

    @GetMapping("/testzipkin-client01")
    public String testZipkin(@RequestParam("client02Port") String client02Port,
                             @RequestParam("feignPort") String feignPort) {
        return "testZipkin " + client02Port + " " + feignPort + " " + client01port;
    }
}
