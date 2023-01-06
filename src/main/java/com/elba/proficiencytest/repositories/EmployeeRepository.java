package com.elba.proficiencytest.repositories;

import com.elba.proficiencytest.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findFirstByUsername(String username);
    Optional<Employee> findByEmail(String email);
}
