package io.github.ripetangerine.todolist.exceptions;

import io.github.ripetangerine.todolist.controller.LoginController;
import io.github.ripetangerine.todolist.exceptions.dto.ApiExceptionResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;


@RestControllerAdvice(basePackageClasses = LoginController.class)
public class LoginControllerAdvice {

    @ExceptionHandler(BadCredentialsException.class)
    public String handleRegistrationException(
            BadCredentialsException exception,
            Model model
    ) {

        final ApiExceptionResponse response = new ApiExceptionResponse(exception.getMessage(), HttpStatus.UNAUTHORIZED, LocalDateTime.now());
        model.addAttribute("error", response);
        System.out.println();
        return "login";
    }

}
