package com.yupi.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yupi.demo.model.entity.Note;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface NoteMapper extends BaseMapper<Note> {

}
