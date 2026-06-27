package com.yupi.demo.model.DTO;

import lombok.Data;

@Data
public class NoteAddDTO {
private String title;
private String content;
private Long categoryId;
private Integer isFavorite;
}
