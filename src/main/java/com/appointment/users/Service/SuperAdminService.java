package com.appointment.users.Service;

import com.appointment.users.Repository.OrganisationRepository;
import com.appointment.users.Repository.UserRepository;
import com.appointment.users.dto.AdminRegisterRequest;
import com.appointment.users.dto.OrganisationRegisterRequest;
import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.entity.*;
import com.appointment.users.exception.*;
import com.appointment.users.Repository.*;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SuperAdminService {

    private final OrganisationRepository organisationRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;


    public SuperAdminService(OrganisationRepository organisationRepository,
                             UserRepository userRepository,
                             PasswordEncoder passwordEncoder) {
        this.organisationRepository = organisationRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Create Organisation
    public Organisation createOrganisation(OrganisationRegisterRequest request) {

        organisationRepository.findByRegistrationnumber(request.getRegistrationnumber())
                .ifPresent(org -> {
                    throw new RuntimeException("Organisation already exists");
                });

        Organisation organisation = new Organisation();
        organisation.setName(request.getName());
        organisation.setLocation(request.getLocation());
        organisation.setRegistrationnumber(request.getRegistrationnumber());
        return organisationRepository.save(organisation);
    }

    //  Create Admin for Organisation
    public User createAdminForOrganisation(AdminRegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new UserAlreadyExistsException("Admin already exists with this email");
        }

        Organisation organisation = organisationRepository.findById(request.getOrgId())
                .orElseThrow(() ->new RuntimeException("Organisation already exist"));

        User admin = new User();
        admin.setName(request.getName());
        admin.setEmail(request.getEmail());
        admin.setPassword(passwordEncoder.encode(request.getPassword()));
        admin.setPhone(request.getPhone());
        admin.setRole(Role.ADMIN);
        admin.setIsactive(true);
        admin.setOrganisation(organisation);

        return userRepository.save(admin);
    }

    // Get All Organisations
    public List<Organisation> getAllOrganisations() {
        return organisationRepository.findAll();
    }

    //  Get Admins of Organisation
    public List<User> getAdminsByOrganisation(Long orgId) {

        // Check organisation exists
        organisationRepository.findById(orgId)
                .orElseThrow(() -> new ResourceNotFoundException("Organisation not found"));

        return userRepository.findAllByOrganisation_IdAndRole(orgId,Role.ADMIN)
                .stream()
                .filter(user -> user.getRole() == Role.ADMIN)
                .toList();
    }
}