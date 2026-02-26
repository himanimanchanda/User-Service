package com.appointment.users.Controller;

import com.appointment.users.dto.*;
import com.appointment.users.entity.Organisation;
import com.appointment.users.entity.User;
import com.appointment.users.Service.SuperAdminService;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/superadmin")
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    public SuperAdminController(SuperAdminService superAdminService) {
        this.superAdminService = superAdminService;
    }

    // Create Organisation
    @PostMapping("/create/organisation")
    public OrganizationResponse createOrganisation(@RequestBody OrganisationRegisterRequest request) {
        System.out.println("Api hit successfully,create");
        Organisation organisation =superAdminService.createOrganisation(request);
        return new OrganizationResponse(organisation);
    }

    // Create Admin for Organisation
    @PostMapping("/onboard/admin")
    public UserResponse createAdmin(@RequestBody AdminRegisterRequest request) {

        User admin = superAdminService.createAdminForOrganisation(request);
        return new UserResponse(admin);
    }

    // Get All Organisations
    @GetMapping("/all/organisations")
    public List<Organisation> getAllOrganisations() {
        return superAdminService.getAllOrganisations();
    }

    // Get Admins of Organisation
    @GetMapping("/organisation/{orgId}/admins")
    public List<UserResponse> getAdminsByOrganisation(@PathVariable Long orgId) {
        return superAdminService.getAdminsByOrganisation(orgId)
                .stream()
                .map(UserResponse::new)
                .toList();
    }
}