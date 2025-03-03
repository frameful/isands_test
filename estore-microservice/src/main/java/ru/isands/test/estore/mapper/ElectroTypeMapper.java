package ru.isands.test.estore.mapper;

import ru.isands.test.estore.model.ElectroType;
import ru.isands.test.estore.model.dto.ElectroTypeDto;
import ru.isands.test.estore.model.dto.input.ElectroTypeInputDto;

public class ElectroTypeMapper {
    public static ElectroTypeDto toDto(ElectroType electroType) {
        return ElectroTypeDto.builder()
                .id(electroType.getId())
                .name(electroType.getName())
                .build();
    }

    public static ElectroType toModel(ElectroTypeInputDto electroTypeInputDto) {
        ElectroType electroType = new ElectroType();
        electroType.setName(electroTypeInputDto.getName());
        return electroType;
    }

    public static ElectroType updateModel(ElectroType electroType, ElectroTypeInputDto electroTypeInputDto) {
        electroType.setName(electroTypeInputDto.getName());
        return electroType;
    }
}
