package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.model.PositionType;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeDto {

    @Schema(description = "Id сотрудника")
    private Long id;

    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @Schema(description = "Отчество сотрудника")
    private String patronymic;

    @Schema(description = "День рождения сотрудника")
    private Date birthDate;

    @Schema(description = "Должность сотрудника")
    private PositionType positionType;

    @Schema(description = "Пол сотрудника")
    private boolean gender;

    private List<ElectroTypeDto> electroTypes;

    @Schema(description = "Магазин, в котором работает сотрудник")
    private ShopOutputDto shop;

}
