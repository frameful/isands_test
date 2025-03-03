package ru.isands.test.estore.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectroItemDto {

    private Long id;

    private String name;

    private Long price;

    private Long count;

    private Boolean archive;

    private String description;

    private ElectroTypeDto electroType;

}
