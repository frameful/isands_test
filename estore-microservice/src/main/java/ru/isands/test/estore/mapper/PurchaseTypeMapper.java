package ru.isands.test.estore.mapper;

import ru.isands.test.estore.model.PurchaseType;
import ru.isands.test.estore.model.dto.PurchaseTypeDto;
import ru.isands.test.estore.model.dto.input.PurchaseTypeInputDto;

public class PurchaseTypeMapper {

    public static PurchaseTypeDto toDto(PurchaseType purchaseType) {
        return PurchaseTypeDto.builder()
                .id(purchaseType.getId())
                .name(purchaseType.getName())
                .build();
    }

    public static PurchaseType toModel(PurchaseTypeInputDto purchaseTypeInputDto) {
        PurchaseType purchaseType = new PurchaseType();
        purchaseType.setName(purchaseTypeInputDto.getName());
        return purchaseType;
    }

    public static PurchaseType updateModel(PurchaseType purchaseType, PurchaseTypeInputDto purchaseTypeInputDto) {
        purchaseType.setName(purchaseTypeInputDto.getName());
        return purchaseType;
    }
}
