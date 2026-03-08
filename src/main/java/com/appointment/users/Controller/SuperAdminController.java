package com.appointment.users.Controller;
import com.appointment.users.Service.SuperAdminService;
import com.appointment.users.dto.AdminRegisterRequest;
import com.appointment.users.dto.OrganisationRegisterRequest;
import com.appointment.users.dto.OrganizationResponse;
import com.appointment.users.dto.UserResponse;
import com.appointment.users.entity.Organisation;
import com.appointment.users.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/superadmin")
@RequiredArgsConstructor
public class SuperAdminController {

    private final SuperAdminService superAdminService;

    @PostMapping("/create-organisation")
    public ResponseEntity<OrganizationResponse> createOrg(@RequestBody OrganisationRegisterRequest req) {
        return ResponseEntity.ok(superAdminService.createOrganisation(req));
    }

    @PostMapping("/onboard-admin")
    public ResponseEntity<UserResponse> createAdmin(@RequestBody AdminRegisterRequest req) {
        return ResponseEntity.ok(superAdminService.createAdminForOrganisation(req));
    }

    @GetMapping("/all-organisations")
    public ResponseEntity<List<Organisation>> getAll() {
        return ResponseEntity.ok(superAdminService.getAllOrganisations());
    }
}