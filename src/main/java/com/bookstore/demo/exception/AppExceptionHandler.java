package com.bookstore.demo.exception;

import com.bookstore.demo.constant.ResponseStatusEnum;
import com.bookstore.demo.dto.CustomResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
@Slf4j
public class AppExceptionHandler {

    @ExceptionHandler(BookStoreException.class)
    public ResponseEntity<Object> handleBookStoreException(BookStoreException exp) {
        log.error("BookStoreException occurred! :- {}", exp.getMessage());
        CustomResponse<String> response = CustomResponse.<String>builder()
                .status(ResponseStatusEnum.FAILURE)
                .message("BookStoreException occurred")
                .data(exp.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleException(Exception exp) {
        log.error("Exception occurred! {} - {}", exp.getLocalizedMessage(), exp);
        CustomResponse<String> response = CustomResponse.<String>builder()
                .status(ResponseStatusEnum.FAILURE)
                .message("Internal server error. Please contact admin")
                .data(exp.getMessage()).build();
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        CustomResponse<Map<String, String>> response = CustomResponse.<Map<String, String>>builder()
                .status(ResponseStatusEnum.FAILURE)
                .message("Validation error")
                .data(errors).build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}