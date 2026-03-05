package com.appointment.users.Controller;

import com.appointment.users.Security.JWTUtil;
import com.appointment.users.Service.AuthService;
import com.appointment.users.dto.*;
import com.appointment.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor // Manual constructor hat gaya
public class AuthController {

    private final AuthService authService;
    private final JWTUtil jwt;

    @PostMapping("/signup")
    public UserResponse signup(@RequestBody UserRegisterRequest userdata) {
        User user = authService.signup(userdata);
        return new UserResponse(user);
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return authService.login(loginRequest);

    }
}