package com.tr.d_teknoloji.notebook.model.dto.audit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseUserDateAuditDTO extends BaseDateAuditDTO {

    protected Long createdBy;
    protected Long updatedBy;
}
