package com.elba.proficiencytest.controllers;

import com.elba.proficiencytest.dtos.DepartmentEmployeesDTO;
import com.elba.proficiencytest.services.DepartmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping("/employees")
    public ResponseEntity<List<DepartmentEmployeesDTO>> getDepartmentsEmployees() {
        return departmentService.getDepartmentsEmployees();
    }
}
