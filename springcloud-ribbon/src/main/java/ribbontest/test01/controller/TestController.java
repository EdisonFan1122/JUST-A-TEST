package ribbontest.test01.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import ribbontest.test01.service.TestService;

@ResponseBody
@RestController
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("/ping")
    public String ping(){
        return testService.test();
    }
}
