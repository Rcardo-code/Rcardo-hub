package com.yupi.demo.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.demo.common.ApiResult;
import com.yupi.demo.model.DTO.NoteAddDTO;
import com.yupi.demo.model.DTO.NoteQueryDTO;
import com.yupi.demo.model.DTO.NoteUpdateDTO;
import com.yupi.demo.model.entity.Note;
import com.yupi.demo.service.NoteService;
import com.yupi.demo.service.impl.NoteServiceImpl;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("note")
public class NoteController {
    private final NoteService noteService;

    public NoteController(NoteServiceImpl noteServiceImpl) {
        this.noteService = noteServiceImpl;
    }

    @PostMapping("add")
    public ApiResult<String> addNote(@RequestBody NoteAddDTO dto) {
        return noteService.addNote(dto);
    }

    @PostMapping("list")    //http://localhost:8081/api/note/list   {"pageNum":1,"pageSize":5,"keyword":""}
    public ApiResult<Page<Note>> listNotes(@RequestBody NoteQueryDTO dto) {
        return noteService.queryNote(dto);
    }

    @PostMapping("update")
    public ApiResult<String> updateNote(@RequestBody NoteUpdateDTO dto) {
        return noteService.updateNote(dto);
    }

    @PostMapping("delete")
    public ApiResult<String> deleteNote(@RequestParam Long id) {
        return noteService.deleteNote(id);
    }

    @PostMapping(deleteBatch)
    public ApiResult<String> deleteBatch(@RequestParam long id){return noteService.deleteBatch();}


}
