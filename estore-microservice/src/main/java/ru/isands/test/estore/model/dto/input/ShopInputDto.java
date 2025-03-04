package ru.isands.test.estore.model.dto.input;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.isands.test.estore.model.dto.input.EmployeeInputDto;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopInputDto {

    @Schema(description = "Название магазина")
    private String name;

    @Schema(description = "Адрес магазина")
    private String address;

}
