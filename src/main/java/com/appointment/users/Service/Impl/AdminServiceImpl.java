package com.appointment.users.Service.Impl;

import com.appointment.users.Repository.OrganisationRepository;
import com.appointment.users.Repository.UserRepository;
import com.appointment.users.Security.JWTUtil;
import com.appointment.users.Service.AdminService;
import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.dto.UserResponse;
import com.appointment.users.entity.Organisation;
import com.appointment.users.entity.Role;
import com.appointment.users.entity.User;
import com.appointment.users.exception.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository usp;
    private final PasswordEncoder pse;
    private final OrganisationRepository orr;
    private final JWTUtil jwtUtil; // JWT logic ab yahan hai

    @Override
    public UserResponse onboardService(UserRegisterRequest urr, HttpServletRequest request) {
        Long orgId = extractOrgId(request);

        if (urr.getRole() == Role.PATIENT) {
            throw new RuntimeException("Admin cannot onboard patients from here");
        }

        usp.findByEmail(urr.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("User with this email already exists");
        });

        Organisation org = orr.findById(orgId)
                .orElseThrow(() -> new RuntimeException("Organisation not found"));

        User user = new User();
        user.setEmail(urr.getEmail());
        user.setName(urr.getName());
        user.setRole(urr.getRole() == null ? Role.DOCTOR : urr.getRole());
        user.setPassword(pse.encode(urr.getPassword()));
        user.setIsactive(true);
        user.setPhone(urr.getPhone());
        user.setOrganisation(org);

        return new UserResponse(usp.save(user)); // Direct Response DTO return
    }

    @Override
    public UserResponse changeActiveStatusService(Long id, HttpServletRequest request) {
        Long orgId = extractOrgId(request);

        User user = usp.findByIdAndOrganisation_Id(id, orgId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found in your organisation"));

        user.setIsactive(!user.isIsactive());
        return new UserResponse(usp.save(user));
    }

    @Override
    public List<UserResponse> getAllEmpService(HttpServletRequest request) {
        Long orgId = extractOrgId(request);
        return usp.findAllByOrganisation_Id(orgId)
                .stream()
                .map(UserResponse::new)
                .toList();
    }

    // Private helper inside Service for token parsing
    private Long extractOrgId(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            throw new RuntimeException("Invalid or missing token");
        }
        String token = authHeader.substring(7);
        return ((Number) jwtUtil.validateToken(token).get("orgId")).longValue();
    }
}