package com.elba.proficiencytest.repositories;

import com.elba.proficiencytest.dtos.ViewEmployeeDTO;
import com.elba.proficiencytest.entities.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findFirstByUsername(String username);
    Optional<Employee> findByEmail(String email);

    @Query("SELECT new com.elba.proficiencytest.dtos.ViewEmployeeDTO(e.firstName, e.lastName, e.username, e.email, " +
            " e.phone, concat(a.city, ', ' , a.neighborhood ), e.startDate, e.endDate, e.status, " +
            " m.username, d.name )" +
            " FROM Employee e " +
            " LEFT JOIN Address a ON e.address.id = a.id " +
            " LEFT JOIN Department d ON e.department.id = d.id " +
            " LEFT JOIN Employee m ON e.manager.id = m.id " +
            " WHERE lower(e.firstName) LIKE %:search% " +
            " OR lower(e.lastName) LIKE %:search% " +
            " OR lower(e.username) LIKE %:search% " +
            " OR lower(e.email) LIKE %:search% ")
    List<ViewEmployeeDTO> findAllBySearch(@Param("search") String search);

    @Query("SELECT new com.elba.proficiencytest.dtos.ViewEmployeeDTO(e.firstName, e.lastName, e.username, e.email, " +
            " e.phone, concat(a.city, ', ' , a.neighborhood ), e.startDate, e.endDate, e.status, " +
            " m.username, d.name )" +
            " FROM Employee e " +
            " LEFT JOIN Address a ON e.address.id = a.id " +
            " LEFT JOIN Department d ON e.department.id = d.id " +
            " LEFT JOIN Employee m ON e.manager.id = m.id " +
            " WHERE e.status = 'ACTIVE' " )
    List<ViewEmployeeDTO> findAllByStatusIsActive();

    @Query("SELECT new com.elba.proficiencytest.dtos.ViewEmployeeDTO(e.firstName, e.lastName, e.username, e.email, " +
            " e.phone, concat(a.city, ', ' , a.neighborhood ), e.startDate, e.endDate, e.status, " +
            " m.username, d.name )" +
            " FROM Employee e " +
            " LEFT JOIN Address a ON e.address.id = a.id " +
            " LEFT JOIN Department d ON e.department.id = d.id " +
            " LEFT JOIN Employee m ON e.manager.id = m.id " +
            " WHERE e.status = 'INACTIVE' " )
    List<ViewEmployeeDTO> findAllByStatusIsInactive();
}
