package com.appointment.users.Controller;

import com.appointment.users.Service.UserService;
import com.appointment.users.dto.OrganizationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/all/organizations")
    public List<OrganizationResponse> getAllOrganizations() {
        return userService.getAllOrganizationService();
    }
}
