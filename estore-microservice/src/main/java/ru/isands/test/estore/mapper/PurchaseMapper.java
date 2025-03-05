package ru.isands.test.estore.mapper;

import ru.isands.test.estore.model.Purchase;
import ru.isands.test.estore.model.dto.ElectroItemDto;
import ru.isands.test.estore.model.dto.PurchaseDto;

public class PurchaseMapper {
    public static PurchaseDto toDto(Purchase purchase) {
        return PurchaseDto.builder()
                .id(purchase.getId())
                .employee(EmployeeMapper.toDto(purchase.getEmployee()))
                .electroItemDto(ElectroItemMapper.toDto(purchase.getElectroItem()))
                .shopDto(ShopMapper.toDto(purchase.getShop()))
                .purchaseType(PurchaseTypeMapper.toDto(purchase.getPurchaseType()))
                .purchaseDate(purchase.getPurchaseDate())
                .build();
    }
}
