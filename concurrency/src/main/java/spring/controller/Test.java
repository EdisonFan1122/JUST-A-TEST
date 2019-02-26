package spring.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import spring.Cache.RedisClient;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


/**
 * Created by Edison on 2018/5/26.
 */
@RestController
@Slf4j
@EnableSwagger2
@Api(value = "Swagger Test Control", description = "演示Swagger用法的Control类", tags = "Swagger Test Control Tag")
@RequestMapping("/concurrency")
public class Test {

    @Autowired
    RedisClient redisClient;

    /**
     * test
     *
     * @author fyf
     * @return String
     */
    @ApiOperation(value = "ping",notes = "测试是否ping通")
    @GetMapping("ping")
    @ResponseBody
    public String ping() {
        log.info("pong");
        return "pong";
    }

    /**
     * redisget
     *
     * @author fyf
     * @return String
     */
    @ApiOperation(value = "获取value",notes = "根据key获取value")
    @ApiImplicitParam(name = "key",value = "键值key",required = true,dataType = "String",paramType = "path")
    @GetMapping("cache/{key}")
    @ResponseBody
    public String get(@PathVariable String key) {
        return redisClient.get(key);
    }

    /**
     * redisset
     *
     * @author fyf
     * @return String
     */
    @ApiOperation(value = "设置键值对",notes = "根据key和value存储")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "key", value = "键值key", required = true, dataType = "String", paramType = "path"),
            @ApiImplicitParam(name = "value", value = "键值value", required = true, dataType = "String", paramType = "path")
    })
    @PostMapping("cache")
    @ResponseBody
    public void set(@PathVariable String key,@PathVariable String value) {
        redisClient.set(key,value);
    }

}
