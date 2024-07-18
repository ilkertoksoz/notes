package com.tr.d_teknoloji.notebook.model.jpa.audit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.tr.d_teknoloji.notebook.enums.DataStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;

@Getter
@Setter
@ToString
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(
        value = {"status"},
        allowGetters = true)
public abstract class DateStatusAudit extends DateAudit implements Serializable {

    @Column(name = "DATA_STATUS")
    @Enumerated(EnumType.ORDINAL)
    protected DataStatus status = DataStatus.ACTIVE;

}
