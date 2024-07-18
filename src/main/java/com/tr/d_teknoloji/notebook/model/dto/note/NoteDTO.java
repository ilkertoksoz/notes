package com.tr.d_teknoloji.notebook.model.dto.note;

import com.tr.d_teknoloji.notebook.model.dto.audit.BaseDateAuditDTO;
import com.tr.d_teknoloji.notebook.model.dto.category.CategoryDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class NoteDTO extends BaseDateAuditDTO {

    @Override
    @Schema(description = "Note id", example = "1")
    public Long getId() {
        return super.getId();
    }

    @NotBlank(message = "Title is required")
    @Schema(description = "Note Title", example = "Meeting Notes")
    private String title;

    @Schema(description = "Note Content", example = "Discussion about the new project...")
    private String content;

    @Schema(description = "Note Category")
    private CategoryDTO category;

    @Schema(description = "Note Category ID")
    @NotNull(message = "Note categoryId is required")
    private Long categoryId;
}
