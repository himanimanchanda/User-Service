package com.appointment.users.Service;

import com.appointment.users.Repository.OrganisationRepository;
import com.appointment.users.Repository.UserRepository;
import com.appointment.users.dto.UserRegisterRequest;
import com.appointment.users.dto.UserResponse;
import com.appointment.users.entity.Organisation;
import com.appointment.users.entity.Role;
import com.appointment.users.entity.User;
import com.appointment.users.exception.UserAlreadyExistsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    private final UserRepository usp;
    private final PasswordEncoder pse;
    private final OrganisationRepository orr;
    public AdminService(UserRepository userRepository,PasswordEncoder paswordEncoder, OrganisationRepository orr){
        this.usp= userRepository;
        this.pse=paswordEncoder;
        this.orr=orr;
    }
    public User onboardService(UserRegisterRequest urr, Long orgId){
       if(urr.getRole()==Role.PATIENT)throw new RuntimeException("invalid role");
       usp.findByEmail(urr.getEmail()).ifPresent(user -> {
            throw new UserAlreadyExistsException("user already exist");
        });
        Organisation org=orr.findById(orgId).orElseThrow(()-> new RuntimeException("organisation not found"));
        User user=new User();
        user.setEmail(urr.getEmail());
        user.setName(urr.getName());
        user.setRole(urr.getRole()==null? Role.DOCTOR:urr.getRole());
        user.setPassword(pse.encode(urr.getPassword()));
        user.setIsactive(true);
        user.setPhone(urr.getPhone());
        user.setOrganisation(org);
        return usp.save(user);
    }
    public User changeActiveStatusService(Long id,Long orgId){
        User user =usp.findByIdAndOrganisation_Id(id,orgId).orElseThrow(() -> new UsernameNotFoundException("User not found with this id"));
        boolean activestatus = user.isIsactive();
        user.setIsactive(!activestatus);
        return usp.save(user);
    }

    // get all or fetch  employees of organization
    public List<UserResponse> getAllEmpService(Long orgId) {
        List<User> userdata = usp.findAllByOrganisation_Id(orgId);
        return userdata.
                stream().
                map(UserResponse::new).toList();

    }
}
