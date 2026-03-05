package com.appointment.users.Service.Impl;

import com.appointment.users.Repository.UserRepository;
import com.appointment.users.Security.JWTUtil;
import com.appointment.users.Service.AdminService;
import com.appointment.users.Service.AuthService;
import com.appointment.users.dto.LoginRequest;
import com.appointment.users.dto.LoginResponse;
import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.dto.UserResponse;
import com.appointment.users.entity.Role;
import com.appointment.users.entity.User;
import com.appointment.users.exception.AccountDisabledException;
import com.appointment.users.exception.InvalidCredentialsException;
import com.appointment.users.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userrepository;
    private final PasswordEncoder passwordEncoder;
    private final JWTUtil jwtUtil;

    @Override
    public LoginResponse login(LoginRequest loginRequest) {
        String email= loginRequest.getEmail();
        String password= loginRequest.getPassword();
        User userdata = userrepository.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        if (!passwordEncoder.matches(password, userdata.getPassword())) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        if (!userdata.isIsactive()) {
            throw new AccountDisabledException("Account is disabled");
        }
        UserResponse userResponse=new UserResponse(userdata);
        // Token generation with null safety for orgId
        Long orgId = (userdata.getOrganisation() != null) ? userdata.getOrganisation().getId() : null;

        String token = jwtUtil.generateToken(userdata.getId(), userdata.getRole(), orgId);

        LoginResponse loginResponse=new LoginResponse(token,userResponse);
        return loginResponse;
    }

    @Override
    public User signup(UserRegisterRequest urr) {
        userrepository.findByEmail(urr.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("Email already exists");
        });

        User userdata = new User();
        userdata.setName(urr.getName());
        userdata.setEmail(urr.getEmail());
        userdata.setPassword(passwordEncoder.encode(urr.getPassword()));

        // Default Role PATIENT agar specify nahi kiya
        userdata.setRole(urr.getRole() != null ? urr.getRole() : Role.PATIENT);

        userdata.setPhone(urr.getPhone());
        userdata.setIsactive(true);

        return userrepository.save(userdata);
    }
}