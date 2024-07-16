package com.tr.d_teknoloji.notebook.repository;

import com.tr.d_teknoloji.notebook.mapper.NoteMapper;
import com.tr.d_teknoloji.notebook.model.dto.note.NoteDTO;
import com.tr.d_teknoloji.notebook.model.jpa.note.Note;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends BaseRepository<Note, NoteDTO, Long, NoteMapper>, QuerydslPredicateExecutor<Note> {
}
