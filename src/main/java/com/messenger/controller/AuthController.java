package com.messenger.controller;

import com.messenger.dto.model.LoginDto;
import com.messenger.dto.model.RegisterDto;
import com.messenger.dto.model.Response;
import com.messenger.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public Response<Map<String, String>> registerUser(@Valid @RequestBody RegisterDto registerDto) {
        return new Response<>(authService.registerUser(registerDto));
    }

    @PostMapping("/login")
    public Response<Map<String, String>> loginUser(@Valid @RequestBody LoginDto loginDto) {
        return new Response<>(authService.loginUser(loginDto));
    }

    // TODO refreshToken
}
