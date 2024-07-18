package com.tr.d_teknoloji.notebook.controller.note;

import com.tr.d_teknoloji.notebook.api.note.NoteAPI;
import com.tr.d_teknoloji.notebook.model.dto.note.NoteDTO;
import com.tr.d_teknoloji.notebook.service.note.NoteService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import static com.tr.d_teknoloji.notebook.constant.AppConstants.API_BASE_PATH;

@Slf4j
@RestController
@RequestMapping(API_BASE_PATH + "/notes")
public class NoteController implements NoteAPI {

    private static final int DEFAULT_PAGE_SIZE = 999999;

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @Override
    @GetMapping
    public ResponseEntity<Page<NoteDTO>> findNotes(
            @PageableDefault(size = DEFAULT_PAGE_SIZE, sort = {"title"}) Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Long categoryId) {

        log.debug("Finding notes with parameters: {}, {}, {}", title, content, categoryId);
        return ResponseEntity.ok(noteService.findAll(title, content, categoryId, pageable));
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<NoteDTO> findNoteById(@PathVariable final Long id) {
        log.debug("Finding note with id: {}", id);
        return ResponseEntity.ok(noteService.findById(id));
    }

    @Override
    @PostMapping
    public ResponseEntity<NoteDTO> createNote(@Valid @RequestBody final NoteDTO dto) {
        log.debug("Creating note: {}", dto);
        dto.setId(null);
        return ResponseEntity.status(HttpStatus.CREATED).body(noteService.save(dto));
    }

    @Override
    @PutMapping
    public ResponseEntity<NoteDTO> updateNote(@Valid @RequestBody final NoteDTO dto) {
        log.debug("Updating note: {}", dto);
        return ResponseEntity.ok(noteService.save(dto));
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteNote(@PathVariable final Long id) {
        log.debug("Deleting note with id: {}", id);
        noteService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

