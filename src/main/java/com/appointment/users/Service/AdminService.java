package com.appointment.users.Service;

import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.dto.UserResponse;
import jakarta.servlet.http.HttpServletRequest;
import java.util.List;

public interface AdminService {
    // Ab ye direct UserResponse dega aur token request se khud nikalega
    UserResponse onboardService(UserRegisterRequest urr, HttpServletRequest request);

    UserResponse changeActiveStatusService(Long id, HttpServletRequest request);

    List<UserResponse> getAllEmpService(HttpServletRequest request);
}