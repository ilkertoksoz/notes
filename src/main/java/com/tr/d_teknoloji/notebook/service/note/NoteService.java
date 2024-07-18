package com.tr.d_teknoloji.notebook.service.note;

import com.tr.d_teknoloji.notebook.mapper.note.NoteMapper;
import com.tr.d_teknoloji.notebook.model.dto.note.NoteDTO;
import com.tr.d_teknoloji.notebook.model.jpa.note.Note;
import com.tr.d_teknoloji.notebook.repository.note.NoteRepository;
import com.tr.d_teknoloji.notebook.service.BaseService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.tr.d_teknoloji.notebook.model.jpa.note.QNote.note;

@Service
public class NoteService extends BaseService<NoteDTO, NoteRepository, Long> {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;

    public NoteService(NoteRepository noteRepository, NoteMapper noteMapper) {
        this.noteRepository = noteRepository;
        this.noteMapper = noteMapper;
    }

    public Page<NoteDTO> findAll(
            final String title,
            final String content,
            final Long categoryId,
            final Pageable pageable) {

        Page<Note> notePage =
                noteRepository.findAll(
                        note.id.isNotNull()
                                .and(title != null ? note.title.containsIgnoreCase(title) : null)
                                .and(content != null ? note.content.containsIgnoreCase(content) : null)
                                .and(categoryId != null ? note.category.id.eq(categoryId) : null),
                        pageable);

        return new PageImpl<>(noteMapper.toDto(notePage.getContent()), pageable, notePage.getTotalElements());
    }
}