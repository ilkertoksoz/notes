package com.tr.d_teknoloji.notebook.exception;

import com.tr.d_teknoloji.notebook.payload.ExceptionResponse;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.MethodNotAllowedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@ControllerAdvice
public class GeneralExceptionHandler {

    private GeneralExceptionHandler(){}
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    static ResponseEntity<ExceptionResponse> resolveConstraintViolationException(final ConstraintViolationException ex) {
        final List<String> messages = new ArrayList<>();
        ex.getConstraintViolations().forEach(error ->
                messages.add(error.getPropertyPath() + " - " + error.getMessage()));
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ExceptionResponse(messages,
                        HttpStatus.CONFLICT.getReasonPhrase(),
                        HttpStatus.CONFLICT.value()));
    }

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    static ResponseEntity<ExceptionResponse> resolveAppException(final AppException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionResponse(List.of(ex.getMessage()),
                        ResponseCodes.INTERNAL_SERVER_ERROR_VALUE,
                        ex.messageId));
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    static ResponseEntity<ExceptionResponse> resolveResourceNotFoundException(final ResourceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ExceptionResponse(List.of(ex.getMessage()),
                        ResponseCodes.NOT_FOUND_GENERAL_VALUE,
                        ResponseCodes.NOT_FOUND_GENERAL));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    static ResponseEntity<ExceptionResponse> resolveEntityNotFoundException(
            final EntityNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(
                        new ExceptionResponse(
                                List.of(ex.getMessage()),
                                ResponseCodes.NOT_FOUND_GENERAL_VALUE,
                                ResponseCodes.NOT_FOUND_GENERAL));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static ResponseEntity<ExceptionResponse> resolveMethodArgumentNotValidException(final MethodArgumentNotValidException ex) {
        final List<String> messages = new ArrayList<>();
        ( ex.getBindingResult()).getAllErrors().forEach(error ->
                messages.add(error.getDefaultMessage()));
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(messages, HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static ResponseEntity<ExceptionResponse> resolveMethodArgumentTypeMismatchException(final MethodArgumentTypeMismatchException ex) {
        final String message = "Parameter '" + ex.getName() + "' must be '" + Objects.requireNonNull(ex.getRequiredType()).getSimpleName() + "'";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(List.of(message), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(MethodNotAllowedException.class)
    @ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ResponseBody
    public static ResponseEntity<ExceptionResponse> resolveHttpRequestMethodNotSupportedException(final HttpRequestMethodNotSupportedException ex) {
        final String message = "Request method '" + ex.getMethod() + "' not supported. List of all supported methods - " + ex.getSupportedHttpMethods();
        return ResponseEntity.status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new ExceptionResponse(List.of(message), HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase(), HttpStatus.METHOD_NOT_ALLOWED.value()));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static ResponseEntity<ExceptionResponse> resolveHttpMessageNotReadableException(Exception e) {
        final String message = "Please provide Request Body in valid JSON format";
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponse(List.of(message), HttpStatus.BAD_REQUEST.getReasonPhrase(), HttpStatus.BAD_REQUEST.value()));
    }

    @ExceptionHandler(UsernameNotFoundException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    static ResponseEntity<ExceptionResponse> resolveUsernameNotFoundException() {
        final String message = "Invalid username or password";
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(
                        new ExceptionResponse(
                                List.of(message),
                                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                                HttpStatus.UNAUTHORIZED.value()));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    static ResponseEntity<ExceptionResponse> resolveIllegalArgumentException() {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(
                        new ExceptionResponse(
                                List.of(""),
                                HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                                HttpStatus.INTERNAL_SERVER_ERROR.value()));
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    static ResponseEntity<ExceptionResponse> resolveDataIntegrityViolationException(final DataIntegrityViolationException ex) {
        // Extract the root cause message
        String errorMessage = Objects.requireNonNullElse(ex.getRootCause(), ex).getMessage();

        // Create a custom error message
        String message = errorMessage == null ? "Duplicate key violation: Unknown cause" : "Duplicate key violation: " + errorMessage;

        // Create ExceptionResponse object
        var errorResponse = ExceptionResponse.builder()
                .error("Data Integrity Violation")
                .status(HttpStatus.BAD_REQUEST.value())
                .message(message)
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }
}
