package com.elba.proficiencytest.services;

import com.elba.proficiencytest.dtos.EmployeesByStatusDTO;
import com.elba.proficiencytest.dtos.ViewEmployeeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class EmployeeServiceTest {

    @Autowired
    private EmployeeService employeeService;

    @Test
    void itShouldCheckNumberOfActiveEmployees() {

        EmployeesByStatusDTO employeesByStatusDTO =  employeeService.getEmployeesByStatus().getBody();

        assertEquals(3, employeesByStatusDTO.getActive().size());
    }

    @Test
    void itShouldCheckNumberOfInactiveEmployees() {

        EmployeesByStatusDTO employeesByStatusDTO =  employeeService.getEmployeesByStatus().getBody();

        assertEquals(25, employeesByStatusDTO.getInactive().size());
    }

    @Test
    void itShouldSearchForEmployeeByUsername() {

        String search = "john.doe";
        ViewEmployeeDTO viewEmployeeDTO = employeeService.searchEmployees(search).getBody().get(0);

        assertEquals(search, viewEmployeeDTO.getUsername());
    }
}
