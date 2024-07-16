package com.tr.d_teknoloji.notebook.service;

import com.tr.d_teknoloji.notebook.mapper.NoteMapper;
import com.tr.d_teknoloji.notebook.model.dto.note.NoteDTO;
import com.tr.d_teknoloji.notebook.model.jpa.note.Note;
import com.tr.d_teknoloji.notebook.repository.NoteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NoteService extends BaseService<NoteDTO, NoteRepository, Long> {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

  /*  public Page<NoteDTO> findAll(String title, String content, Long categoryId, Pageable pageable) {

        final Page<Note> notePage = noteRepository.findAll(note.id

        return new PageImpl<>(noteMapper.toDto(notePage.getContent()), pageable, notePage.getTotalElements());
    } */
}