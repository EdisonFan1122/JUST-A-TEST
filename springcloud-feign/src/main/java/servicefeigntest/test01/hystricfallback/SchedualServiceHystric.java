package servicefeigntest.test01.hystricfallback;

import org.springframework.stereotype.Component;
import servicefeigntest.test01.service.SchedualService;

@Component
public class SchedualServiceHystric implements SchedualService {

    @Override
    public String ping(){
        return "SORRY,FEIGN-ERROR!";
    }

}
