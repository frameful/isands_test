package ru.isands.test.estore.mapper;

import ru.isands.test.estore.model.PositionType;
import ru.isands.test.estore.model.dto.PositionTypeDto;
import ru.isands.test.estore.model.dto.input.PositionTypeInputDto;

public class PositionTypeMapper {

    public static PositionTypeDto toDto(PositionType positionType) {
        return PositionTypeDto.builder()
                .id(positionType.getId())
                .name(positionType.getName())
                .build();
    }

    public static PositionType toModel(PositionTypeInputDto positionTypeInputDto) {
        PositionType positionType = new PositionType();
        positionType.setName(positionTypeInputDto.getName());
        return positionType;
    }

    public static PositionType updateModel(PositionType positionType, PositionTypeInputDto positionTypeInputDto) {
        positionType.setName(positionTypeInputDto.getName());
        return positionType;
    }
}
