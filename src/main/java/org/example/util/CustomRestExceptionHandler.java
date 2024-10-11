package org.example.util;

import org.example.util.error.EntityNotFoundException;
import org.example.util.error.ErrorResponse;
import org.example.util.error.PermissionViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import org.thymeleaf.exceptions.TemplateInputException;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(EntityNotFoundException.class)
    protected ResponseEntity<ErrorResponse> handleEntityNotFoundException(EntityNotFoundException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        return new ResponseEntity<>(new ErrorResponse(status, ex.getMessage(), ex.getReason()), status);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    protected ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(DataIntegrityViolationException ex) {
        HttpStatus status = HttpStatus.CONFLICT;
        return new ResponseEntity<>(new ErrorResponse(status, ex.getMessage(), "Integrity constraint has been violated."), status);
    }

    @ExceptionHandler(TemplateInputException.class)
    protected ResponseEntity<ErrorResponse> handleTemplateInputException(TemplateInputException ex) {
        HttpStatus status = HttpStatus.BAD_REQUEST;
        return new ResponseEntity<>(new ErrorResponse(status, ex.getMessage(), "wrong url path"), status);
    }

    @ExceptionHandler(PermissionViolationException.class)
    protected ResponseEntity<ErrorResponse> handlePermissionViolationException(PermissionViolationException ex) {
        HttpStatus status = HttpStatus.FORBIDDEN;
        return new ResponseEntity<>(new ErrorResponse(status, ex.getMessage(), ex.getReason()), status);
    }
}
