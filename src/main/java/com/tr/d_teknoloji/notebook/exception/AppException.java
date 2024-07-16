package com.tr.d_teknoloji.notebook.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class AppException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    public final Integer messageId;

    public AppException(String message) {
        super(message);
        this.messageId = ErrorCodes.GENERAL;
    }

    public AppException(String message, Throwable cause) {
        super(message, cause);
        this.messageId = ErrorCodes.GENERAL;
    }

    public AppException(Integer messageId) {
        super("Error code: " + messageId);
        this.messageId = messageId;
    }

    public Integer getMessageId() {
        return messageId;
    }

    private static class ErrorCodes {
        static final Integer GENERAL = 100;
    }
}
