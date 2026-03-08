package com.appointment.users.Service.Impl;

import com.appointment.users.Repository.OrganisationRepository;
import com.appointment.users.Repository.UserRepository;
import com.appointment.users.Service.SuperAdminService;
import com.appointment.users.dto.AdminRegisterRequest;
import com.appointment.users.dto.OrganisationRegisterRequest;
import com.appointment.users.dto.OrganizationResponse;
import com.appointment.users.dto.UserResponse;
import com.appointment.users.entity.Organisation;
import com.appointment.users.entity.Role;
import com.appointment.users.entity.User;
import com.appointment.users.exception.ResourceNotFoundException;
import com.appointment.users.exception.UserAlreadyExistsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SuperAdminServiceImpl implements SuperAdminService {

    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public OrganizationResponse createOrganisation(OrganisationRegisterRequest request) {
        organisationRepository.findByRegistrationnumber(request.getRegistrationnumber())
                .ifPresent(org -> {
                    throw new UserAlreadyExistsException("Organisation already exists!");
                });

        Organisation org = new Organisation();
        org.setName(request.getName());
        org.setLocation(request.getLocation());
        org.setRegistrationnumber(request.getRegistrationnumber());
        return new OrganizationResponse(organisationRepository.save(org));
    }

    @Override
    public UserResponse createAdminForOrganisation(AdminRegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Email already taken!");
        }

        Organisation org = organisationRepository.findById(request.getOrgId())
                .orElseThrow(() -> new ResourceNotFoundException("Org not found!"));

        User admin = new User();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setPhone(request.getPhone());
        admin.setRole(Role.ADMIN);
        admin.setOrganisation(org);
        admin.setIsactive(true);
        return new UserResponse(userRepository.save(admin));
    }

    @Override
    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }


}