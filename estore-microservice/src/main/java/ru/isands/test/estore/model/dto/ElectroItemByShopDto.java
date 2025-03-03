package ru.isands.test.estore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectroItemByShopDto {

    private Long id;

    private String name;

    private Long price;

    private Boolean archive;

    private String description;

    private ElectroTypeDto electroType;

    private Map<ShopOutputDto, Long> electroItemCountByShop;

}