--数据库初始化脚本
 --创建数据库
CREATE database seckill;

--使用数据库seckill
CREATE TABLE seckill(
  'serkill_id' bigint NOT NULL AUTO_INCREMENT DEFAULT COMMENT='商品库存ID',
  'name'VARCHAR (120) NOT NULL COMMENT='商品名称',
  'number' int NOT NULL COMMENT='库存数量',
  'start_time' TIMESTAMP NOT NULL COMMENT='秒杀开启时间',
  'end_time' TIMESTAMP NOT NULL COMMENT='秒杀结束时间',
  'create_time' TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT='创建时间',
PRIMARY KEY (seckill_id),
key idx_start_time(start_time),
key idx_end_time(end_time),
key idx_create_time(create_time)
)ENGINE=innoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=UTF8 COMMENT='秒杀库存表';

--初始化数据
INSERT INTO seckill(name,number,start_time,end_time)
VALUES
  ('3000元秒杀iphone10','100','2018-10-01 00:00:00','2018-10-02 00:00:00'),
  ('300元秒杀ipad4','500','2018-10-01 00:00:00','2018-10-02 00:00:00'),
  ('1000元秒杀mix2s','100','2018-10-01 00:00:00','2018-10-02 00:00:00'),
  ('1000元秒杀mate20','400','2018-10-01 00:00:00','2018-10-02 00:00:00');

--秒杀成功明细表
--用户登录认证相关信息
CREATE TABLE success_killed(
  'serkill_id' bigint NOT NULL AUTO_INCREMENT DEFAULT COMMENT='商品库存ID',
  'user_phone'bigint NOT NULL COMMENT='用户手机号',
  'state' tinyint NOT NULL DEFAULT -1 COMMENT='-1:无效 0:成功 1:已付款',
  'create_time' TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT='创建时间',
PRIMARY KEY (seckill_id,user_phone),
key idx_create_time(create_time)
)ENGINE=innoDB DEFAULT CHARSET=UTF8 COMMENT='秒杀成功明细表';
