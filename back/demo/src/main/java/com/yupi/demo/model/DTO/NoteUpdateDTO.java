package com.yupi.demo.model.DTO;

import lombok.Data;

@Data
public class NoteUpdateDTO {
    //笔记主键id，必须传，确定改哪一条
    private Long id;
    private String title;
    private String content;
    private Long categoryId;
    private Integer isFavorite;
    //归档状态也在这里修改
    private Integer isArchived;
}