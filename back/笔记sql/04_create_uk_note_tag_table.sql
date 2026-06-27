use sparknotes;

-- 标签表
create table `tag` (
    id bigint auto_increment primary key comment '标签ID',
    user_id bigint not null comment '所属用户ID',
    name varchar(50) not null comment '标签名称',
    color varchar(7) default '#1890ff' comment '标签颜色（HEX格式）',
    create_time datetime not null default current_timestamp comment '创建时间',
    update_time datetime not null default current_timestamp on update current_timestamp comment '更新时间',
    is_deleted tinyint(1) default 0 comment '逻辑删除',
    index idx_user_id (user_id)
) engine=InnoDB default charset=utf8mb4 comment='标签表';

-- 笔记-标签关联表
create table `note_tag` (
    id bigint auto_increment primary key comment '关联ID',
    note_id bigint not null comment '笔记ID',
    tag_id bigint not null comment '标签ID',
    create_time datetime not null default current_timestamp comment '创建时间',
    unique key uk_note_tag (note_id, tag_id),
    index idx_note_id (note_id),
    index idx_tag_id (tag_id)
) engine=InnoDB default charset=utf8mb4 comment='笔记-标签关联表';