package com.tr.d_teknoloji.notebook.mapper.note;

import com.tr.d_teknoloji.notebook.mapper.BaseEntityMapper;
import com.tr.d_teknoloji.notebook.model.dto.note.NoteDTO;
import com.tr.d_teknoloji.notebook.model.jpa.note.Note;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface NoteMapper extends BaseEntityMapper<NoteDTO, Note> {

}
