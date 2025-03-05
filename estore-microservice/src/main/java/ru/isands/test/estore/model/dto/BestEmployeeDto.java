package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.model.ElectroType;
import ru.isands.test.estore.model.PositionType;
import ru.isands.test.estore.model.Purchase;
import ru.isands.test.estore.model.Shop;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BestEmployeeDto {

    private Long id;

    @Schema(description = "Имя сотрудника")
    private String firstName;

    @Schema(description = "Фамилия сотрудника")
    private String lastName;

    private Long count;

}
