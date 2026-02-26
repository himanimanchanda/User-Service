package com.appointment.users.dto;

import com.appointment.users.entity.Organisation;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data

public class OrganizationResponse {
    private Long orgId;
    private String name;
    private String location;
    private String registrationnumber;

    public OrganizationResponse(Organisation organisation){
        this.name= organisation.getName();
        this.location= organisation.getLocation();
        this.registrationnumber= organisation.getRegistrationnumber();
        this.orgId=organisation.getId();


    }
}
