package com.tr.d_teknoloji.notebook.api.note;

import com.tr.d_teknoloji.notebook.model.dto.note.NoteDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@Tag(name = "Note", description = "API for managing Notes")
@SecurityRequirement(name = "access_token")
public interface NoteAPI {

    @Operation(method = "GET", summary = "Find Notes", operationId = "findNotes")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Successful operation")})
    ResponseEntity<Page<NoteDTO>> findNotes(
            @PageableDefault() @Parameter(hidden = true) Pageable pageable,
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String content,
            @RequestParam(required = false) Long categoryId);

    @Operation(method = "GET", summary = "Find Note by ID", operationId = "findNoteById")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Note not found")
            })
    ResponseEntity<NoteDTO> findNoteById(@PathVariable Long id);

    @Operation(method = "POST", summary = "Create Note", operationId = "createNote")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Note created"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    ResponseEntity<NoteDTO> createNote(@RequestBody NoteDTO dto);

    @Operation(method = "PATCH", summary = "Update Note", operationId = "updateNote")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Successful operation"),
                    @ApiResponse(responseCode = "404", description = "Note not found"),
                    @ApiResponse(responseCode = "400", description = "Invalid input")
            })
    ResponseEntity<NoteDTO> updateNote(@RequestBody NoteDTO dto);

    @Operation(method = "DELETE", summary = "Delete Note by ID", operationId = "deleteNote")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "204", description = "Note deleted"),
                    @ApiResponse(responseCode = "404", description = "Note not found")
            })
    ResponseEntity<Void> deleteNote(@PathVariable Long id);
}