package com.appointment.users.Service;;

import com.appointment.users.dto.AdminRegisterRequest;
import com.appointment.users.dto.OrganisationRegisterRequest;
import com.appointment.users.entity.Organisation;
import com.appointment.users.entity.User;
import java.util.List;

public interface SuperAdminService {
    Organisation createOrganisation(OrganisationRegisterRequest request);
    User createAdminForOrganisation(AdminRegisterRequest request);
    List<Organisation> getAllOrganisations();
    List<User> getAdminsByOrganisation(Long orgId);
}