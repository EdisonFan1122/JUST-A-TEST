package com.fyf.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Edison on 2018/10/9.
 */
@Data
@Entity
@Table(name = "success_killed")
public class SuccessKilled implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seckill_id", nullable = false, length = 50)
    private long secKillId;

    @Column(name = "user_phone", nullable = false)
    private long userPhone;

    @Column(name = "state", nullable = false)
    private short state;

    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Column(name = "create_time", nullable = false)
    private Date createTime;
}


/**
 * JPA的注解来定义实体的时候，使用@Id来注解主键属性即可。如果数据库主键是自增长的，需要在增加一个注解@GeneratedValue
 *
 * @GeneratedValue 注解的strategy属性提供四种值：
 * –AUTO： 主键由程序控制，是默认选项，不设置即此项。
 * –IDENTITY：主键由数据库自动生成，即采用数据库ID自增长的方式，Oracle不支持这种方式。
 * –SEQUENCE：通过数据库的序列产生主键，通过@SequenceGenerator 注解指定序列名，mysql不支持这种方式。
 * –TABLE：通过特定的数据库表产生主键，使用该策略可以使应用更易于数据库移植。
 * <p>
 * 如果某个实体类的字段包含 Date类型，那么数据库中应该存储的是 “yyyy-MM-dd hh:MM:ss”的形式，针对这种形式的存储，@Temporal 有三种注解值对应。
 * 第一种：@Temporal(TemporalType.DATE)--->实体类会封装成日期“yyyy-MM-dd”的 Date类型。
 * 第二种：@Temporal(TemporalType.TIME)--->实体类会封装成时间“hh-MM-ss”的 Date类型。
 * 第三种：@Temporal(TemporalType.TIMESTAMP)--->实体类会封装成完整的时间“yyyy-MM-dd hh:MM:ss”的 Date类型。
 * @JsonFormat 此注解用于属性或者方法上（最好是属性上），可以方便的把Date类型直接转化为我们想要的模式，
 * @JsonFormat 默认情况下timeZone为GMT（即标准时区），所以会造成输出少8小时。
 * @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Shanghai")
 * 作用：1）入参时，请求报文只需要传入yyyy-MM-dd HH-mm-ss字符串进来，则自动转换为Date类型数据。
 * 2）出参时，Date类型的数据自动转换为yyyy-MM-dd HH-mm-ss字符串返回出去。
 */