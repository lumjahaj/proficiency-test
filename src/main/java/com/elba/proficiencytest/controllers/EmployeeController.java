package com.elba.proficiencytest.controllers;

import com.elba.proficiencytest.services.EmployeeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping
    public String hello() {
        return "hello";
    }

    @PostMapping("/upload")
    public ResponseEntity<?> uploadDataFromFile(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(employeeService.uploadDataFromFile(file));
    }
}
