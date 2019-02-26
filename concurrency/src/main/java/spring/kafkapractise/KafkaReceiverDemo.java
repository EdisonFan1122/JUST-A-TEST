package spring.kafkapractise;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import java.util.Optional;

/**
 * Created by Edison on 2018/10/16.
 */
//@Component
@Slf4j
public class KafkaReceiverDemo {

//    @KafkaListener(topicPattern = "showcase.*")
    @KafkaListener(topics = "${topicname}")
    public static void listen(ConsumerRecord<?,?> record){
        Optional<?> kafkaMessage = Optional.ofNullable(record);
        if(kafkaMessage.isPresent()){
            Object message = kafkaMessage.get();
            log.info("record {}",record);
            log.info("message {}",message);
        }
    }
}


/**
 *   @KafkaListener(topicPattern = "showcase.*")
 *   @KafkaListener(topics = "${topicname}")
 */
