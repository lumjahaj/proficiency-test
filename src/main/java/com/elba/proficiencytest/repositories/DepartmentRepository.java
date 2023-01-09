package com.elba.proficiencytest.repositories;

import com.elba.proficiencytest.dtos.DepartmentEmployeesDTO;
import com.elba.proficiencytest.entities.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {

    Optional<Department> findByName(String name);

    @Query("SELECT new com.elba.proficiencytest.dtos.DepartmentEmployeesDTO(d.name, COUNT(e.id)) " +
            " FROM Department d " +
            " LEFT JOIN Employee e ON d.id = e.department.id " +
            " GROUP BY d.id ")
    List<DepartmentEmployeesDTO> findNameAndNumberOfEmployees();

    @Query("SELECT e.lastName " +
            " FROM Department d " +
            " LEFT JOIN Employee e on d.id = e.department.id " +
            " WHERE d.name = ?1 ")
    List<String> findDepartmentEmployeesLastNames(String departmentName);

}
