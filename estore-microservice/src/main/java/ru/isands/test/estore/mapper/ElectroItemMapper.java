package ru.isands.test.estore.mapper;

import ru.isands.test.estore.model.ElectroItem;
import ru.isands.test.estore.model.dto.ElectroItemByShopDto;
import ru.isands.test.estore.model.dto.ElectroItemDto;
import ru.isands.test.estore.model.dto.ShopOutputDto;
import ru.isands.test.estore.model.dto.input.ElectroItemInputDto;

import java.util.Map;

public class ElectroItemMapper {
    public static ElectroItemDto toDto(ElectroItem electroItem) {
        return ElectroItemDto.builder()
                .id(electroItem.getId())
                .price(electroItem.getPrice())
                .count(electroItem.getCount())
                .electroType(ElectroTypeMapper.toDto(electroItem.getElectroType()))
                .archive(electroItem.getArchive())
                .description(electroItem.getDescription())
                .name(electroItem.getName())
                .build();
    }

    public static ElectroItem toModel(ElectroItemInputDto electroItemInputDto) {
        ElectroItem electroItem = new ElectroItem();
        electroItem.setName(electroItemInputDto.getName());
        electroItem.setDescription(electroItemInputDto.getDescription());
        electroItem.setPrice(electroItemInputDto.getPrice());
        electroItem.setArchive(electroItemInputDto.getArchive() == 1);
        return electroItem;
    }

    public static ElectroItem updateModel(ElectroItem electroItem, ElectroItemInputDto electroItemInputDto) {
        electroItem.setArchive(electroItemInputDto.getArchive() == 1);
        electroItem.setPrice(electroItemInputDto.getPrice() == null ? electroItem.getPrice() : electroItemInputDto.getPrice());
        electroItem.setName(electroItemInputDto.getName() == null ? electroItem.getName() : electroItemInputDto.getName());
        electroItem.setDescription(electroItemInputDto.getDescription() == null ? electroItem.getDescription() : electroItemInputDto.getDescription());
        return electroItem;
    }

    public static ElectroItemByShopDto toByShopDto(ElectroItem electroItem, Map<ShopOutputDto, Long> electroItemCountByShop) {
        return ElectroItemByShopDto.builder()
                .id(electroItem.getId())
                .price(electroItem.getPrice())
                .electroType(ElectroTypeMapper.toDto(electroItem.getElectroType()))
                .archive(electroItem.getArchive())
                .description(electroItem.getDescription())
                .name(electroItem.getName())
                .electroItemCountByShop(electroItemCountByShop)
                .build();
    }
}
