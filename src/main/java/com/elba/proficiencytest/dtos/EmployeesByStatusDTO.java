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
public class EmployeesByStatusDTO {
    private List<ViewEmployeeDTO> active;

    private List<ViewEmployeeDTO> inactive;
}
