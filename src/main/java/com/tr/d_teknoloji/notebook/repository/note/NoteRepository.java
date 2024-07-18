package com.tr.d_teknoloji.notebook.repository.note;

import com.tr.d_teknoloji.notebook.mapper.note.NoteMapper;
import com.tr.d_teknoloji.notebook.model.dto.note.NoteDTO;
import com.tr.d_teknoloji.notebook.model.jpa.note.Note;
import com.tr.d_teknoloji.notebook.repository.BaseRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface NoteRepository extends BaseRepository<Note, NoteDTO, Long, NoteMapper>, QuerydslPredicateExecutor<Note> {
}
