package com.appointment.users.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class OrganisationRegisterRequest {
    private String name;
    private String location;
    private String registrationnumber;
}
