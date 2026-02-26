package com.appointment.users.Service;

import com.appointment.users.Repository.OrganisationRepository;
import com.appointment.users.Repository.UserRepository;
import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.entity.Role;
import com.appointment.users.entity.User;
import com.appointment.users.exception.AccountDisabledException;
import com.appointment.users.exception.InvalidCredentialsException;
import com.appointment.users.exception.UserAlreadyExistsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository usp;
    private final PasswordEncoder pse;
    private final OrganisationRepository orr;

    public AuthService(UserRepository usp, PasswordEncoder pse, OrganisationRepository orr) {
        this.usp = usp;
        this.pse = pse;
        this.orr = orr;
    }

    public User login(String email, String password) {
        User userdata = usp.findByEmail(email)
                .orElseThrow(() -> new InvalidCredentialsException("Invalid credentials"));

        // Debugging ke liye
        boolean isMatch = pse.matches(password, userdata.getPassword());
        System.out.println("Login Step - Match Result: " + isMatch);
        System.out.println("Using Encoder: " + pse.getClass().getName());
        if (!isMatch) {
            throw new InvalidCredentialsException("Invalid Credentials");
        }

        if (!userdata.isIsactive()) {
            throw new AccountDisabledException("Account is disabled");
        }
        return userdata;
    }

    public User signup(UserRegisterRequest urr) {
        usp.findByEmail(urr.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("Email already existed");
        });

        User userdata = new User();
        userdata.setName(urr.getName());
        userdata.setEmail(urr.getEmail());
        userdata.setPassword(pse.encode(urr.getPassword()));

        // Pehle hardcoded Role.PATIENT tha
        // Ab agar Request mein role bheja hai toh wo set hoga, warna default PATIENT
        if (urr.getRole() != null) {
            userdata.setRole(urr.getRole());
        } else {
            userdata.setRole(Role.PATIENT);
        }

        userdata.setPhone(urr.getPhone());
        userdata.setIsactive(true);

        return usp.save(userdata);
    }
}
