package ru.isands.test.estore.model.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectroItemInputDto {

    private String name;

    private Long price;

    private Integer archive;

    private String description;

    private Long count;

    private Long electroTypeId;

}
