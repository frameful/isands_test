package ru.isands.test.estore.mapper;

import ru.isands.test.estore.model.Shop;
import ru.isands.test.estore.model.dto.ShopDto;
import ru.isands.test.estore.model.dto.ShopOutputDto;
import ru.isands.test.estore.model.dto.input.ShopInputDto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ShopMapper {

    public static ShopDto toDto(Shop shop) {
        return ShopDto.builder()
                .id(shop.getId())
                .address(shop.getAddress())
                .name(shop.getName())
                .employees(shop.getEmployees().stream().map(EmployeeMapper::toOutputDto).collect(Collectors.toList()))
                .build();
    }

    public static Shop toModel(ShopInputDto shopInputDto) {
        Shop shop = new Shop();
        shop.setName(shopInputDto.getName());
        shop.setAddress(shopInputDto.getAddress());
        shop.setEmployees(new ArrayList<>());
        return shop;
    }

    public static ShopOutputDto toOutputDto(Shop shop) {
        return ShopOutputDto.builder()
                .id(shop.getId())
                .address(shop.getAddress())
                .name(shop.getName())
                .build();
    }

    public static Shop updateModel(Shop shop, ShopInputDto shopInputDto) {
        shop.setName(shopInputDto.getName() == null ? shop.getName() : shopInputDto.getName());
        shop.setAddress(shopInputDto.getAddress() == null ? shop.getAddress() : shopInputDto.getAddress());

        return shop;
    }
}
