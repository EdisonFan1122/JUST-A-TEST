package springcloud.client02.hystricfallback;

import org.springframework.stereotype.Component;
import springcloud.client02.service.Client02Service;

@Component
public class Client02ServiceHystric implements Client02Service {

    @Override
    public String testZipkin(String client02Port) {
        return "SORRY,CLIENT02-TESTZKPIN-ERROR!";
    }

}
