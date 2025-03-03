package ru.isands.test.estore.model.dto.input;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchaseInputDto {

    private Long electroId;

    private Long employeeId;

    private Long typeId;

    private Long shopId;

}
