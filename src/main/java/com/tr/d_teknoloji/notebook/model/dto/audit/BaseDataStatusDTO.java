package com.tr.d_teknoloji.notebook.model.dto.audit;

import com.tr.d_teknoloji.notebook.enums.DataStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseDataStatusDTO extends BaseDTO {

    @Schema(description = "Ilgili kaydin deleted olup olmadigini tutar.", example = "0")
    @Enumerated(EnumType.ORDINAL)
    protected DataStatus status = DataStatus.ACTIVE;
}
