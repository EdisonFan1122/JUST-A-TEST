package spring.kafkapractise;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import spring.domain.kafka.MessageTest;

import java.util.Date;

/**
 * Created by Edison on 2018/10/16.
 */
@Slf4j
@Component
public class KafkaUtil {

    private static KafkaTemplate<String, String> kafkaTemplate;

    private static Gson gson = new GsonBuilder().create();

    public static void send(String topic,String partition,String message) {
        MessageTest messageTest = new MessageTest();
        messageTest.setId(System.currentTimeMillis());
        messageTest.setMsg(message);
        messageTest.setSendTime(new Date());
        log.info("messageTest {}",gson.toJson(messageTest));
        kafkaTemplate.send(topic,partition,gson.toJson(messageTest));
    }

}
