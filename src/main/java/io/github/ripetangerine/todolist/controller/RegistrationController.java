package io.github.ripetangerine.todolist.controller;

import io.github.ripetangerine.todolist.dto.RegistrationRequest;
import io.github.ripetangerine.todolist.dto.RegistrationResponse;
import io.github.ripetangerine.todolist.exceptions.RegistrationException;
import io.github.ripetangerine.todolist.security.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;


/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;

    @GetMapping()
    public String registerPage(Model model) {
        model.addAttribute("registrationRequest", new RegistrationRequest());
        return "register";
    }

    @PostMapping
    @Operation(tags = "Register Service", description = "You can register to the system by sending information in the appropriate format.")
    public String register(
            @Valid @ModelAttribute RegistrationRequest request,
            BindingResult bindingResult,
            Model model
    ) {

        if (bindingResult.hasErrors()) {
            return "register";
        }

        try {
            userService.registration(request);
            System.out.println("===== REGISTER SUCCESS =====");
            System.out.println("RETURN REDIRECT LOGIN");
            return "redirect:/login";

        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());

            return "register";
        }
    }

}
