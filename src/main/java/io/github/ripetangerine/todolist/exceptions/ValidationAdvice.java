package io.github.ripetangerine.todolist.exceptions;

import io.github.ripetangerine.todolist.exceptions.dto.ValidationErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.List;


@Slf4j
@RestControllerAdvice
public class ValidationAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public final ResponseEntity<ValidationErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {

        final List<FieldError> fieldErrors = exception.getBindingResult().getFieldErrors();
        final List<String> errorList = fieldErrors.stream().map(DefaultMessageSourceResolvable::getDefaultMessage).toList();

        final ValidationErrorResponse validationErrorResponse = new ValidationErrorResponse(HttpStatus.BAD_REQUEST, LocalDateTime.now(), errorList);

        log.warn("Validation errors : {} , Parameters : {}", errorList, exception.getBindingResult().getTarget());

        return ResponseEntity.status(validationErrorResponse.getStatus()).body(validationErrorResponse);
    }

}
