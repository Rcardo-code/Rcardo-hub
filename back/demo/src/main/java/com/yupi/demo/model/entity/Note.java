package com.yupi.demo.model.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("note")
public class Note {
    private Long id;
    private Long userId;         // 不是 userid
    private String title;
    private String content;
    private Long categoryId;
    private Integer isFavorite;  // 不是 isfavorite
    private Integer isArchived;  // 不是 isarchived
    //创建时间：新增自动填充
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    //更新时间：新增、修改都自动填充
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    @TableLogic
    private Integer isDelete;
}