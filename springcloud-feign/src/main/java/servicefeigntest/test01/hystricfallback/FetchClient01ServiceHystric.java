package servicefeigntest.test01.hystricfallback;

import org.springframework.stereotype.Component;
import servicefeigntest.test01.service.FetchClient01Service;

@Component
public class FetchClient01ServiceHystric implements FetchClient01Service {
    @Override
    public String testZipkin(String client02Port, String feignPort) {
        return "CLIENT02PORT-FEIGNPORT-FETCHCLIENT01-ERROR!";
    }
}
