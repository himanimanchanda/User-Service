package com.appointment.users.Service.Impl;

import com.appointment.users.Repository.OrganisationRepository;
import com.appointment.users.Service.UserService;
import com.appointment.users.dto.OrganizationResponse;
import com.appointment.users.entity.Organisation;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final OrganisationRepository orr;

    @Override
    public List<OrganizationResponse> getAllOrganizationService() {
        return orr.findAll().stream()
                .map(OrganizationResponse::new)
                .toList();
    }
}
