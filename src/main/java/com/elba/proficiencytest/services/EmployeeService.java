package com.elba.proficiencytest.services;

import com.elba.proficiencytest.dtos.*;
import com.elba.proficiencytest.entities.Department;
import com.elba.proficiencytest.entities.Employee;
import com.elba.proficiencytest.entities.Address;
import com.elba.proficiencytest.enums.Status;
import com.elba.proficiencytest.repositories.AddressRepository;
import com.elba.proficiencytest.repositories.DepartmentRepository;
import com.elba.proficiencytest.repositories.EmployeeRepository;
import com.elba.proficiencytest.util.DateUtil;
import com.poiji.bind.Poiji;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final AddressRepository addressRepository;

    public EmployeeService(EmployeeRepository employeeRepository, DepartmentRepository departmentRepository, AddressRepository addressRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
        this.addressRepository = addressRepository;
    }

    public ResponseEntity<List<ViewEmployeeDTO>> searchEmployees(String search) {

        List<ViewEmployeeDTO> viewEmployeeDTOS;

        try {
            search = search.toLowerCase();
            viewEmployeeDTOS = employeeRepository.findAllBySearch(search);
        } catch (Exception e) {
            System.out.println("Exception occurred during find employees by search");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(viewEmployeeDTOS, HttpStatus.OK);
    }

    public ResponseEntity<EmployeesByStatusDTO> getEmployeesByStatus() {
        EmployeesByStatusDTO employeesByStatusDTOS;

        List<ViewEmployeeDTO> viewActiveEmployeeDTOS;
        List<ViewEmployeeDTO> viewInactiveEmployeeDTOS;

        try {
            viewActiveEmployeeDTOS = employeeRepository.findAllByStatusIsActive();
            viewInactiveEmployeeDTOS = employeeRepository.findAllByStatusIsInactive();

            employeesByStatusDTOS = new EmployeesByStatusDTO(viewActiveEmployeeDTOS, viewInactiveEmployeeDTOS);

        } catch (Exception e) {
            System.out.println("Exception occurred during find employees by status");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(employeesByStatusDTOS, HttpStatus.OK);
    }

    public ResponseEntity<List<ViewEmployeeDTO>> getEmployeesInAscendingOrder() {

        List<ViewEmployeeDTO> viewEmployeeDTOS;

        try {
            viewEmployeeDTOS = employeeRepository.findAllOrderByFirstNameAsc();
        } catch (Exception e) {
            System.out.println("Exception occurred during find employees in ascending order");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(viewEmployeeDTOS, HttpStatus.OK);
    }

    public ResponseEntity<?> uploadDataFromFile(MultipartFile multipartFile) {

        File file = new File("src/main/resources/employees.xlsx");

        try {

            Path filepath = Paths.get("src/main/resources", multipartFile.getOriginalFilename());
            multipartFile.transferTo(filepath);

            List<ExcelDataDTO> excelData = Poiji.fromExcel(file, ExcelDataDTO.class);

            Map<String, Department> leaderDepartment = new HashMap<>();
            List<EmployeeRowDTO> employees = new ArrayList<>();

            //create departments and save leader names in a map with the corresponding department
            for (ExcelDataDTO excelRow : excelData) {
                if (excelRow.getDepartmentName() != null) {
                    Department department = new Department();
                    setDepartmentData(excelRow, department);
                    leaderDepartment.put(excelRow.getDepartmentLeader(), departmentRepository.save(department));
                }
            }

            //create employees and set EmployeeRowDTO data to use later when creating relations with manager and department
            for (ExcelDataDTO excelRow : excelData) {

                //if date format in Excel file 'start date' or 'end date' is not valid don't create employee
                try {
                    createEmployee(excelRow);
                } catch (DateTimeParseException e) {
                    continue;
                }

                EmployeeRowDTO employeeRowDTO = new EmployeeRowDTO();
                setEmployeeRowDTO(excelRow, employeeRowDTO);
                employees.add(employeeRowDTO);
            }

            //set leaders for departments
            for (Map.Entry<String, Department> entry : leaderDepartment.entrySet()) {
                Department department = entry.getValue();
                Optional<Employee> leader = employeeRepository.findFirstByUsername(entry.getKey());
                leader.ifPresent(department::setLeader);
                departmentRepository.save(department);
            }

            //set managers and departments for employees
            for (EmployeeRowDTO employeeRow : employees) {
                Optional<Employee> optionalEmployee = employeeRepository.findByEmail(employeeRow.getEmail());

                if (optionalEmployee.isPresent()) {
                    Employee employee = optionalEmployee.get();

                    Optional<Employee> manager = employeeRepository.findFirstByUsername(employeeRow.getManager());
                    manager.ifPresent(employee::setManager);

                    //if department with that name does not exist create a new department
                    Optional<Department> department = departmentRepository.findByName(employeeRow.getDepartment());
                    if (department.isEmpty()) {
                        Department newDepartment = new Department();
                        newDepartment.setName(employeeRow.getDepartment());
                        employee.setDepartment(departmentRepository.save(newDepartment));
                    } else
                        employee.setDepartment(department.get());

                    employeeRepository.save(employee);
                }
            }

        } catch (IOException e) {
            System.out.println("Error while transferring file from MultipartFile to File");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            System.out.println("Error: ");
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public void createEmployee(ExcelDataDTO excelRow) {
        Employee employee = new Employee();

        employee.setFirstName(excelRow.getFullName().split(" ")[0]);
        employee.setLastName(excelRow.getFullName().split(" ")[1]);
        employee.setUsername(excelRow.getUsername());
        employee.setEmail(excelRow.getEmail());
        employee.setPhone(excelRow.getPhoneNumber());
        employee.setStartDate(DateUtil.integerToLocalDate(excelRow.getStartDate()));
        employee.setEndDate(DateUtil.integerToLocalDate(excelRow.getEndDate()));

        if (employee.getEndDate().isBefore(LocalDate.now()))
            employee.setStatus(Status.INACTIVE);
        else
            employee.setStatus(Status.ACTIVE);

        //if address with that name does not exist create a new address
        String city = excelRow.getAddress().split(",")[0];
        String neighborhood = excelRow.getAddress().split(",")[1];
        Optional<Address> address = addressRepository.findByCityAndNeighborhood(city, neighborhood);
        if (address.isEmpty()) {
            Address newAddress = new Address();
            newAddress.setCity(city);
            newAddress.setNeighborhood(neighborhood);
            employee.setAddress(addressRepository.save(newAddress));
        } else
            employee.setAddress(address.get());

        employeeRepository.save(employee);
    }

    public void setDepartmentData(ExcelDataDTO excelRow, Department department) {
        department.setName(excelRow.getDepartmentName());
        department.setPhone(excelRow.getDepartmentPhone());
    }

    public void setEmployeeRowDTO(ExcelDataDTO excelRow, EmployeeRowDTO employeeRowDTO) {
        employeeRowDTO.setEmail(excelRow.getEmail());
        employeeRowDTO.setManager(excelRow.getManager());
        employeeRowDTO.setDepartment(excelRow.getDepartment());
    }
}
