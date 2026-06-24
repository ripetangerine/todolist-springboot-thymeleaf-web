package io.github.ripetangerine.todolist.controller;


import io.github.ripetangerine.todolist.dto.LoginRequest;
import io.github.ripetangerine.todolist.dto.LoginResponse;
import io.github.ripetangerine.todolist.dto.RegistrationRequest;
import io.github.ripetangerine.todolist.security.jwt.JwtTokenService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * Created on Ağustos, 2020
 *
 * @author Faruk
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/login")
public class LoginController {

    private final JwtTokenService jwtTokenService;

    @GetMapping()
    public String loginPage() {
        System.out.println("===== LOGIN GET =====");
        return "login";
    }

    @PostMapping
    @Operation(tags = "Login Service", description = "You must log in with the correct information to successfully obtain the token information.")
    public String loginRequest( @Valid @ModelAttribute LoginRequest request,
                                BindingResult bindingResult,
                                HttpServletResponse response,
                                Model model) {
        if(bindingResult.hasErrors()){
            System.out.println("bindingResult error login >>>"+ bindingResult.hasErrors());
            bindingResult.getAllErrors().forEach(e -> {
                System.out.println(e.toString());
            });
            return "login";
        }
        try{

            String token = jwtTokenService.getLoginResponse(request).getToken();
            ResponseCookie cookie = ResponseCookie.from("accessToken", token)
                    .httpOnly(true)   // JS 접근 차단 (보안)
                    .secure(false)    // HTTPS 쓰면 true
                    .path("/")
                    .maxAge(60 * 60)
                    .sameSite("Lax")
//                    .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))
                    .build();

            response.setHeader("Set-Cookie", cookie.toString());
            return "redirect:/todos";

        } catch (Exception e){
            e.printStackTrace();
            model.addAttribute("error", e.getMessage());
            System.out.println("login error");
            return "login";
        }
    }
}