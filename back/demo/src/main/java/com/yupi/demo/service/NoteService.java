package com.yupi.demo.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.demo.common.ApiResult;
import com.yupi.demo.model.DTO.NoteAddDTO;
import com.yupi.demo.model.DTO.NoteQueryDTO;
import com.yupi.demo.model.DTO.NoteUpdateDTO;
import com.yupi.demo.model.entity.Note;

public interface NoteService {
    ApiResult<String> addNote(NoteAddDTO dto);
    ApiResult<String> updateNote(NoteUpdateDTO dto);
    ApiResult<Page<Note>> queryNote(NoteQueryDTO dto);
    ApiResult<String> deleteNote(Long id);
}
