package com.appointment.users.Service;
import com.appointment.users.Repository.OrganisationRepository;
import com.appointment.users.entity.Organisation;
import com.appointment.users.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
   private final UserRepository usp;
   private final PasswordEncoder pse;
   private final OrganisationRepository orr;
    public UserService(UserRepository userRepository,PasswordEncoder passwordEncoder, OrganisationRepository orr){
        this.usp= userRepository;
        this.pse=passwordEncoder;
        this.orr=orr;

    }
     public List<Organisation> getAllOrganizationService() {
        return orr.findAll();
    }
}

