package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopDto {

    @Schema(description = "Id магазина")
    private Long id;

    @Schema(description = "Название магазина")
    private String name;

    @Schema(description = "Адрес магазина")
    private String address;

    @Schema(description = "Список сотрудников")
    private List<EmployeeOutputDto> employees;

}
