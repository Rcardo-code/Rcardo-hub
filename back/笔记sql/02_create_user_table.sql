use sparknotes;
create table `user` (
id bigint auto_increment primary key,
username varchar(50) not null UNIQUE COMMENT '用户名',
PASSWORD varchar(255) not null COMMent 'BCrypt加密过的密码',
email VARCHAR(100) unique comment'邮箱',
nickname VARCHAR(50) COMMENT'昵称',
create_time datetime not null DEFAULT CURRENT_TIMESTAMP,
update_time datetime not null DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
is_delete tinyint(1) DEFAULT 0
)ENGINE=InnoDB default charset=utf8mb4 comment='用户表';