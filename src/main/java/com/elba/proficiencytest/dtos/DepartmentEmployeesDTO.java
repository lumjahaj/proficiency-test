package com.elba.proficiencytest.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentEmployeesDTO {

    private String departmentName;

    private Long numberOfEmployees;

    private List<String> employeeLastNames;

    public DepartmentEmployeesDTO(String departmentName, Long numberOfEmployees) {
        this.departmentName = departmentName;
        this.numberOfEmployees = numberOfEmployees;
    }
}
