package com.elba.proficiencytest.dtos;

import com.elba.proficiencytest.enums.Status;
import com.elba.proficiencytest.util.DateUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ViewEmployeeDTO {
    private String firstName;

    private String lastName;

    private String username;

    private String email;

    private String phone;

    private String address;

    private String startDate;

    private String endDate;

    private Status status;

    private String managerUsername;

    private String departmentName;

    public ViewEmployeeDTO(String firstName, String lastName, String username, String email, String phone, String address, LocalDate startDate, LocalDate endDate, Status status, String managerUsername, String departmentName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.startDate = DateUtil.localDateToString(startDate);
        this.endDate = DateUtil.localDateToString(endDate);
        this.status = status;
        this.managerUsername = managerUsername;
        this.departmentName = departmentName;
    }
}
