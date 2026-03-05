package com.appointment.users.entity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name="organisation",uniqueConstraints = {
        @UniqueConstraint(columnNames = {"registrationnumber"})
})
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String location;
    @Column(nullable = false)
    private String registrationnumber;

    @OneToMany(mappedBy = "organisation")
    private List<User> users;  // doctors/admins

   }