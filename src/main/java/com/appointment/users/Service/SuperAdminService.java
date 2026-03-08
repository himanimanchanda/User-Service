package com.appointment.users.Service;;

import com.appointment.users.dto.AdminRegisterRequest;
import com.appointment.users.dto.OrganisationRegisterRequest;
import com.appointment.users.dto.OrganizationResponse;
import com.appointment.users.dto.UserResponse;
import com.appointment.users.entity.Organisation;
import com.appointment.users.entity.User;
import java.util.List;

public interface SuperAdminService {
    OrganizationResponse createOrganisation(OrganisationRegisterRequest request);
    UserResponse createAdminForOrganisation(AdminRegisterRequest request);
    List<Organisation> getAllOrganisations();

}