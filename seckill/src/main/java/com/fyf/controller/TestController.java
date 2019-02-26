package com.fyf.controller;

import com.fyf.dto.Exposer;
import com.fyf.dto.SecKillExecution;
import com.fyf.dto.SecKillResult;
import com.fyf.enums.SecKillStatEnum;
import com.fyf.exception.RepeatKillException;
import com.fyf.exception.SecKillCloseException;
import com.fyf.service.SecKillService;
import com.fyf.bean.SecKill;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * Created by Edison on 2018/10/9.
 */


@RestController
public class TestController {

    @GetMapping("/ping")
    public String test(){
        return "PONG";
    }


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SecKillService secKillService;

    @GetMapping(value = "/list")
    public String list(Model model) {
        //获取列表页
        List<SecKill> list = secKillService.getSecKillList();
        model.addAttribute("list", list);
        //list.jsp + model = ModelAndView
        return "list";// /WEB-INF/jsp/"list".jsp
    }

    @GetMapping(value = "/{secKillId}/detail")
    public String detail(@PathVariable("secKillId") Long secKillId, Model model) {
        if (secKillId == null) {
            return "redirect:/secKill/list";
        }
        SecKill secKill = secKillService.getById(secKillId);
        if (secKill == null) {
            return "forward:/secKill/list";
        }
        model.addAttribute("secKill", secKill);
        return "detail";
    }

    //ajax json
    @PostMapping(value = "/{secKillId}/exposer",
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<Exposer> exposer(@PathVariable Long secKillId) {
        SecKillResult<Exposer> result;
        try {
            Exposer exposer = secKillService.exportSecKillUrl(secKillId);
            result = new SecKillResult<Exposer>(true, exposer);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            result = new SecKillResult<Exposer>(false, e.getMessage());
        }
        return result;
    }

    @PostMapping(value = "/{secKillId}/{md5}/execution",
            produces = {"application/json;charset=UTF-8"})
    @ResponseBody
    public SecKillResult<SecKillExecution> execute(@PathVariable("secKillId") Long secKillId,
                                                   @PathVariable("md5") String md5,
                                                   @CookieValue(value = "killPhone", required = false) Long phone) {
        //springmvc valid
        if (phone == null) {
            return new SecKillResult<SecKillExecution>(false, "未注册");
        }
        SecKillResult<SecKillExecution> result;
        try {
            //存储过程调用.
            SecKillExecution execution = secKillService.executeSecKillProcedure(secKillId, phone, md5);
            return new SecKillResult<SecKillExecution>(true,execution);
        } catch (RepeatKillException e) {
            SecKillExecution execution = new SecKillExecution(secKillId, SecKillStatEnum.REPEAT_KILL);
            return new SecKillResult<SecKillExecution>(true,execution);
        } catch (SecKillCloseException e) {
            SecKillExecution execution = new SecKillExecution(secKillId, SecKillStatEnum.END);
            return new SecKillResult<SecKillExecution>(true,execution);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            SecKillExecution execution = new SecKillExecution(secKillId, SecKillStatEnum.INNER_ERROR);
            return new SecKillResult<SecKillExecution>(true,execution);
        }
    }

    @GetMapping(value = "/time/now")
    @ResponseBody
    public SecKillResult<Long> time(){
        Date now = new Date();
        return new SecKillResult(true,now.getTime());
    }


}
