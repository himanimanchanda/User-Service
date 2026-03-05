package com.appointment.users.Service;

import com.appointment.users.dto.LoginRequest;
import com.appointment.users.dto.LoginResponse;
import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.entity.User;

public interface AuthService {
    LoginResponse login(LoginRequest loginRequest);
    User signup(UserRegisterRequest urr);
}
