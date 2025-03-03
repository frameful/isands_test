package ru.isands.test.estore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectroEmployeeDto {

    private EmployeeDto employee;

    private ElectroTypeDto electroType;

}
