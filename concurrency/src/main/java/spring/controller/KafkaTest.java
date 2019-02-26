package spring.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Edison on 2018/10/16.
 */

@RestController
@Slf4j
@RequestMapping("/kafka")
public class KafkaTest {

    @Autowired
    private KafkaTemplate<String,String> kafkaTemplate;

    @GetMapping(value = "/{topic}/send")
    public void sendMeessageTotopic1(@PathVariable String topic,@RequestParam(value = "partition",defaultValue = "0") int partition) {
        log.info("start send message to {}",topic);
        kafkaTemplate.send(topic,partition,"你","好");
    }

}
