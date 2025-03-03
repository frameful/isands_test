package ru.isands.test.estore.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseDto {

    @Schema(description = "Id покупки")
    private Long id;

    @Schema(description = "Сотрудник, привязанный к покупке")
    private EmployeeDto employee;

    @Schema(description = "Купленный электротовар")
    private ElectroItemDto electroItemDto;

    @Schema(description = "Тип покупки")
    private PurchaseTypeDto purchaseType;

    private Date purchaseDate;

    @Schema(description = "Магазин, в котором была совершена покупка")
    private ShopDto shopDto;

}
