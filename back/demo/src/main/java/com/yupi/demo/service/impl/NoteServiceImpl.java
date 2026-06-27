package com.yupi.demo.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yupi.demo.common.ApiResult;
import com.yupi.demo.mapper.NoteMapper;
import com.yupi.demo.model.DTO.NoteAddDTO;
import com.yupi.demo.model.DTO.NoteQueryDTO;
import com.yupi.demo.model.DTO.NoteUpdateDTO;
import com.yupi.demo.model.entity.Note;
import com.yupi.demo.service.NoteService;
import jakarta.annotation.Resource;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteServiceImpl implements NoteService {
    @Resource
    private NoteMapper noteMapper;

    private Long getCurrentUserId() {
        return StpUtil.getLoginIdAsLong();
    }

    @Override
    public ApiResult<String> addNote(NoteAddDTO dto) {
        Long userId = getCurrentUserId();
        Note note = new Note();
        BeanUtils.copyProperties(dto, note);
        note.setUserId(userId);
        int insert = noteMapper.insert(note);
        if (insert > 0) {
            return ApiResult.success("笔记创建成功");
        }
        return ApiResult.serverError("笔记保存失败");
    }

    @Override
    public ApiResult<Page<Note>> queryNote(NoteQueryDTO dto) {
        Page<Note> page = new Page<>(dto.getPageNum(), dto.getPageSize());
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Note::getUserId, getCurrentUserId());

        if (dto.getKeyword() != null && !dto.getKeyword().isEmpty()) {
            wrapper.and(w -> w
                    .like(Note::getTitle, dto.getKeyword())
                    .or()
                    .like(Note::getContent, dto.getKeyword())
            );
        }
        if (dto.getIsArchived() != null) {
            wrapper.eq(Note::getIsArchived, dto.getIsArchived());
        }
        wrapper.orderByDesc(Note::getCreateTime);

        Page<Note> result = noteMapper.selectPage(page, wrapper);
        return ApiResult.success(result);
    }

    @Override
    public ApiResult<String> updateNote(NoteUpdateDTO dto) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Note::getId, dto.getId());
        wrapper.eq(Note::getUserId, getCurrentUserId());

        Note existNote = noteMapper.selectOne(wrapper);
        if (existNote == null) {
            return ApiResult.badRequest("笔记不存在或无权修改");
        }

        // 只更新非空的字段，防止 null 覆盖数据库已有数据
        Note note = new Note();
        note.setId(dto.getId());
        if (dto.getTitle() != null) note.setTitle(dto.getTitle());
        if (dto.getContent() != null) note.setContent(dto.getContent());
        if (dto.getCategoryId() != null) note.setCategoryId(dto.getCategoryId());
        if (dto.getIsFavorite() != null) note.setIsFavorite(dto.getIsFavorite());
        if (dto.getIsArchived() != null) note.setIsArchived(dto.getIsArchived());

        int updated = noteMapper.updateById(note);
        if (updated > 0) {
            return ApiResult.success("笔记更新成功");
        }
        return ApiResult.serverError("笔记更新失败");
    }

    @Override
    public ApiResult<String> deleteNote(Long id) {
        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Note::getId, id);
        wrapper.eq(Note::getUserId, getCurrentUserId());

        Note existNote = noteMapper.selectOne(wrapper);
        if (existNote == null) {
            return ApiResult.badRequest("笔记不存在或无权删除");
        }

        int deleted = noteMapper.deleteById(id);
        if (deleted > 0) {
            return ApiResult.success("笔记删除成功");
        }
        return ApiResult.serverError("笔记删除失败");
    }

    @Override
    public ApiResult<String> deleteBatch(List<Long> ids) {
        // 空列表校验
        if (ids == null || ids.isEmpty()) {
            return ApiResult.badRequest("删除列表不能为空");
        }

        LambdaQueryWrapper<Note> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Note::getUserId, getCurrentUserId())
                .in(Note::getId, ids);

        int deleted = noteMapper.delete(wrapper);
        if (deleted > 0) {
            return ApiResult.success("成功删除 " + deleted + " 条笔记");
        }
        return ApiResult.badRequest("没找到这条笔记");
    }
}