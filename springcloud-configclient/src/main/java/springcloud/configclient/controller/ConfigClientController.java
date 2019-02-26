package springcloud.configclient.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class ConfigClientController {

    @Autowired
    private Environment env;

    @GetMapping("/foo")
    public String getFoo() {
        return env.getProperty("foo", "undefined");
    }

    @Value("${democonfigclient.message}")
    String foo;

    @GetMapping(value = "/foo2")
    public String hi() {
        return foo;
    }
}
