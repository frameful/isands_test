package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ShopOutputDto {

    @Schema(description = "Id магазина")
    private Long id;

    @Schema(description = "Название магазина")
    private String name;

    @Schema(description = "Адрес магазина")
    private String address;

}
