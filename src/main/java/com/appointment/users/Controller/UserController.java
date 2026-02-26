package com.appointment.users.Controller;

import com.appointment.users.Service.UserService;
import com.appointment.users.dto.OrganizationResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/user")

public class UserController {

    private final UserService uss;
    public UserController( UserService uss){
        this.uss=uss;

    }
    @GetMapping("/all/organizations")
    public List<OrganizationResponse> getAllOrganizations(){
        return uss.getAllOrganizationService().stream()
                .map(org -> {
                    OrganizationResponse dto=new OrganizationResponse(org);
                    return dto;
                }).toList();
    }
}
