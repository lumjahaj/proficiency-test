package com.elba.proficiencytest.services;

import com.elba.proficiencytest.dtos.DepartmentEmployeesDTO;
import com.elba.proficiencytest.repositories.DepartmentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    public DepartmentService(DepartmentRepository departmentRepository) {
        this.departmentRepository = departmentRepository;
    }

    public ResponseEntity<List<DepartmentEmployeesDTO>> getDepartmentsEmployees() {

        List<DepartmentEmployeesDTO> departmentEmployeesDTOS;

        try {

            departmentEmployeesDTOS = departmentRepository.findNameAndNumberOfEmployees();
            for (DepartmentEmployeesDTO departmentEmployeesDTO : departmentEmployeesDTOS) {
                departmentEmployeesDTO.setEmployeeLastNames(departmentRepository.findDepartmentEmployeesLastNames(departmentEmployeesDTO.getDepartmentName()));
            }

        } catch (Exception e) {
            System.out.println("Exception occurred during find departments and their employees");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(departmentEmployeesDTOS, HttpStatus.OK);

    }
}
