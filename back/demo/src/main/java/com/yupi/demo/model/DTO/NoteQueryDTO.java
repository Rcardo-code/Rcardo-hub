package com.yupi.demo.model.DTO;

import lombok.Data;

@Data
public class NoteQueryDTO {
    private Integer pageNum=1;
    private Integer pageSize=10;
    private String keyword;
    private Integer isArchived;
}
