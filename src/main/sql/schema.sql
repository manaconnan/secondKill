--数据库初始化脚本

--创建数据库
CREATE DATABASE seckill;

use seckill;

CREATE TABLE seckill(
seckill_id bigint NOT NULL AUTO_INCREMENT COMMENT '商品库存id',
name VARCHAR(120) NOT NULL COMMENT'商品名称',
number int NOT NUll COMMENT '库存数量',
start_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '秒杀开启时间',
end_time TIMESTAMP  NOT NULL DEFAULT  CURRENT_TIMESTAMP comment '秒杀结束时间',
create_time TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP comment '创建时间',
PRIMARY KEY (seckill_id),
KEY idx_start_time(start_time),
KEY idx_end_time(end_time),
KEY idx_create_time(create_time)
)ENGINE = InnoDB AUTO_INCREMENT=1000 DEFAULT CHARSET=utf8 COMMENT='秒杀库存表';

--初始化数据
INSERT INTO
  seckill(name,number,start_time,end_time)
VALUES
  ('1000元秒杀iphone X',100,'2017-9-26 00:00:00','2017-9-27 00:00:00'),
  ('500元秒杀ipad 3',200,'2017-9-26 00:00:00','2017-9-27 00:00:00'),
  ('300元秒杀小米5',300,'2017-9-26 00:00:00','2017-9-27 00:00:00'),
  ('200元秒杀红米note',400,'2017-9-26 00:00:00','2017-9-27 00:00:00');
--秒杀成功明细表
--用户登陆认证相关信息
CREATE TABLE success_killed(
seckill_id INT NOT NULL comment '秒杀商品id',
user_phone bigint NOT NULL comment '用户手机号',
state tinyint NOT NULL DEFAULT -1 comment '状态标示：-1无效，0，成功，1已付款，2已发货',
create_time TIMESTAMP NOT NULL comment '创建时间',
PRIMARY KEY(seckill_id,user_phone),/*这里注意是使用联合主键 */
KEY idx_create_time(create_time)
)ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='秒杀成功明细表';


--连接数据库控制台