package com.tr.d_teknoloji.notebook.payload;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.tr.d_teknoloji.notebook.exception.ResponseCodes;
import lombok.*;

import java.time.Instant;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ExceptionResponse {
    private String error;
    @Builder.Default
    private Integer status = ResponseCodes.GENERAL;
    @Singular
    private List<String> messages;
    @Builder.Default
    private Instant timestamp = Instant.now();
    @JsonInclude(JsonInclude.Include.NON_NULL) // Exclude null fields from JSON output
    private Integer messageId;

    public ExceptionResponse(final List<String> messages, final String error, final Integer status) {
        this.messages = messages;
        this.error = error;
        this.status = status;
        this.timestamp = Instant.now();
    }
}
