package com.appointment.users.Controller;

import com.appointment.users.Security.JWTUtil;
import com.appointment.users.Service.AdminService;
import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.dto.UserResponse;
import com.appointment.users.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminService adminService;

    @PostMapping("/onboard-emp")
    public UserResponse onboard(@RequestBody UserRegisterRequest ur, HttpServletRequest request) {
        return adminService.onboardService(ur, request);
    }

    @GetMapping("/show-emps")
    public List<UserResponse> getAllUser(HttpServletRequest request) {
        return adminService.getAllEmpService(request);
    }

    @PatchMapping("/changeactivestatus/user/{id}")
    public UserResponse changeActivestatus(@PathVariable long id, HttpServletRequest request) {
        return adminService.changeActiveStatusService(id, request);
    }
}