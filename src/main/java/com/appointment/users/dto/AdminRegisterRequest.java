package com.appointment.users.dto;

import com.appointment.users.entity.Organisation;
import com.appointment.users.entity.Role;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AdminRegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;
    private String phone;
    private long orgId;
}
