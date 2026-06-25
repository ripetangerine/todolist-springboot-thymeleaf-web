package io.github.ripetangerine.todolist.exceptions;

import io.github.ripetangerine.todolist.controller.RegistrationController;
import io.github.ripetangerine.todolist.exceptions.dto.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@RestControllerAdvice(basePackageClasses = RegistrationController.class)
public class RegistrationControllerAdvice {

    @ExceptionHandler(RegistrationException.class)
    public String handleRegistrationException(
            RegistrationException exception,
            Model model
    ) {

        final ApiExceptionResponse response = new ApiExceptionResponse(exception.getErrorMessage(), HttpStatus.BAD_REQUEST, LocalDateTime.now());
        model.addAttribute("error", response);
//        return ResponseEntity.status(response.getStatus()).body(response);
        return "register";
    }
}
