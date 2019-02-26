package spring.domain.kafka;

import lombok.Data;

import java.util.Date;

/**
 * Created by Edison on 2018/10/16.
 */
@Data
public class MessageTest {
    private Long id;

    private String msg;

    private Date sendTime;
}
