package com.elba.proficiencytest.dtos;

import com.poiji.annotation.ExcelCell;
import com.poiji.annotation.ExcelRow;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ExcelDataDTO {
    @ExcelRow
    private int rowIndex;

    @ExcelCell(0)
    private String fullName;

    @ExcelCell(1)
    private String manager;

    @ExcelCell(2)
    private String username;

    @ExcelCell(3)
    private String email;

    @ExcelCell(4)
    private String department;

    @ExcelCell(5)
    private String phoneNumber;

    @ExcelCell(6)
    private String address;

    @ExcelCell(7)
    private int startDate;

    @ExcelCell(8)
    private int endDate;

    @ExcelCell(11)
    private String departmentName;

    @ExcelCell(12)
    private String departmentLeader;

    @ExcelCell(13)
    private String departmentPhone;
}
