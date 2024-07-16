package com.tr.d_teknoloji.notebook.model.dto.category;

import com.tr.d_teknoloji.notebook.model.dto.audit.BaseDateAuditDTO;
import com.tr.d_teknoloji.notebook.model.dto.note.NoteDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class CategoryDTO extends BaseDateAuditDTO {

    @Override
    @Schema(description = "Category id", example = "1")
    public Long getId() {
        return super.getId();
    }

    @NotBlank(message = "Category name is required")
    @Schema(description = "Category Name", example = "Work")
    private String name;

    @Schema(description = "Notes in the category")
    private List<NoteDTO> notes;
}
