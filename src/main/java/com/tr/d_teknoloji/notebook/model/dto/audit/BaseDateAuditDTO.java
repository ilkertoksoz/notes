package com.tr.d_teknoloji.notebook.model.dto.audit;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Date;


@Data
@ToString(callSuper = true)
@EqualsAndHashCode(callSuper = true)
public class BaseDateAuditDTO extends BaseDataStatusDTO {

    protected Date createdAt;
    protected Date updatedAt;
    protected Long version;
}
