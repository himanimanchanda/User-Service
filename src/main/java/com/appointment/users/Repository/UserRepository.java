package com.appointment.users.Repository;

import com.appointment.users.entity.Role;
import com.appointment.users.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    List<User> findByRole(Role role);
    List<User> findAllByOrganisation_IdAndRole(Long orgId,Role role);
    Optional<User> findByIdAndOrganisation_Id(Long id,Long orgId);
    // UserRepository.java mein ye add karein:
    List<User> findAllByOrganisation_Id(Long orgId);
}

