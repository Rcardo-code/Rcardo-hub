-- 03_create_note_table.sql

use sparknotes;

create table `note` (
id bigint auto_increment primary key comment '笔记ID',
user_id bigint not null comment '所属用户ID',
title varchar(200) not null comment '笔记标题',
content text comment '笔记内容（Markdown格式）',
category_id bigint default null comment '分类ID',
is_favorite tinyint(1) default 0 comment '是否收藏',
is_archived tinyint(1) default 0 comment '是否归档',
create_time datetime not null default CURRENT_TIMESTAMP comment '创建时间',
update_time datetime not null default CURRENT_TIMESTAMP on update CURRENT_TIMESTAMP comment '更新时间',
is_delete tinyint(1) default 0 comment '逻辑删除',
index idx_user_id (user_id),
index idx_category_id (category_id)
) engine=InnoDB default charset=utf8mb4 comment='笔记表';