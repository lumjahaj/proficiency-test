package com.elba.proficiencytest.controllers;

import com.elba.proficiencytest.dtos.EmployeesByStatusDTO;
import com.elba.proficiencytest.dtos.ViewEmployeeDTO;
import com.elba.proficiencytest.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public ResponseEntity<List<ViewEmployeeDTO>> searchEmployees(@RequestParam("search") String search) {
        return employeeService.searchEmployees(search);
    }

    @GetMapping("/status")
    public ResponseEntity<EmployeesByStatusDTO> getEmployeesByStatus() {
        return employeeService.getEmployeesByStatus();
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDataFromFile(@RequestParam("file") MultipartFile file) {
        return employeeService.uploadDataFromFile(file);
    }
}
