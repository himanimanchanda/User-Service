package com.appointment.users.Repository;
import com.appointment.users.entity.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {

    // Optional: find by name
    Optional<Organisation> findByName(String name);
    Optional<Organisation> findByRegistrationnumber(String registraionnumber);
}
