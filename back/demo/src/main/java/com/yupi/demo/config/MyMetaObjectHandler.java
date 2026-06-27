package com.yupi.demo.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;

// @Component 标记交给Spring管理，就是配置类标识
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {

    //插入时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        strictInsertFill(metaObject,"createTime", LocalDateTime::now, LocalDateTime.class);
        strictInsertFill(metaObject,"updateTime", LocalDateTime::now, LocalDateTime.class);
    }

    //更新时自动填充
    @Override
    public void updateFill(MetaObject metaObject) {
        strictUpdateFill(metaObject,"updateTime", LocalDateTime::now, LocalDateTime.class);
    }
}