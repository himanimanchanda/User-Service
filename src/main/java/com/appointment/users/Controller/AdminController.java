package com.appointment.users.Controller;

import com.appointment.users.Security.JWTUtil;
import com.appointment.users.Service.AdminService;
import com.appointment.users.Service.UserService;
import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.dto.UserResponse;
import com.appointment.users.entity.User;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminController {
    private final UserService userService;
    private final AdminService adminService;
    private final JWTUtil jwt;
    public AdminController(UserService uss, JWTUtil jwt,AdminService admin) {
        this.userService = uss;
        this.adminService=admin;
        this.jwt=jwt;
    }

    @PostMapping("/onboard")
    public UserResponse onboard(@RequestBody UserRegisterRequest ur, HttpServletRequest request) {
        String auth= request.getHeader("authorization");
        if(auth==null || !auth.startsWith("Bearer")) throw new RuntimeException("missing token");
        String token=auth.substring(7);
        Claims claims= jwt.validateToken(token);
        Number orgIdNum = (Number) claims.get("orgId");
        Long orgId = orgIdNum.longValue();
        System.out.println("orgId from token: " + orgId);
        User user = adminService.onboardService(ur,orgId);
        UserResponse response = new UserResponse(user);
        return response;
    }

    //    admin/allusers-- this api will fetch all the saved users from db and map it to UserResponse dto and return ,it calls getAllUSerService mehod from service
    @GetMapping("/org/showEmps")
    public List<UserResponse> getAllUser(HttpServletRequest request) {
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")) throw new RuntimeException("Invalid or missing token");

        String token=authHeader.substring(7);
        Claims claims=jwt.validateToken(token);

        Number orgIdNum = (Number) claims.get("orgId");
        Long orgId = orgIdNum.longValue();
        return adminService.getAllEmpService(orgId);

    }
//    change active status by id
    @PatchMapping("/changeactivestatus/user/{id}")
    public UserResponse changeActivestatus(@PathVariable long id,HttpServletRequest request){
        String authHeader=request.getHeader("Authorization");
        if(authHeader==null || !authHeader.startsWith("Bearer ")) throw new RuntimeException("Invalid or missing token");
        String token=authHeader.substring(7);
        Claims claims=jwt.validateToken(token);

        Number orgIdNum = (Number) claims.get("orgId");
        Long orgId = orgIdNum.longValue();
        User user =adminService.changeActiveStatusService(id,orgId);
        return new UserResponse(user);
        }

    }

