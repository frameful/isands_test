package ru.isands.test.estore.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.model.dto.ShopDto;

import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmployeeInputDto {

    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    @Schema(description = "Отчество сотрудника")
    private String patronymic;

    @Schema(description = "День рождения сотрудника")
    private String birthDate;

    @Schema(description = "Должность сотрудника")
    private Long positionId;

    private Long shopId;

    @Schema(description = "Пол сотрудника")
    private Integer gender;

}
