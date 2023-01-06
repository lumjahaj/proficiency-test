package com.elba.proficiencytest.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @ManyToOne
    @JoinColumn(name = "leader_id")
    private Employee leader;

    @OneToMany(mappedBy = "department")
    private Set<Employee> employees = new HashSet<>();

}
