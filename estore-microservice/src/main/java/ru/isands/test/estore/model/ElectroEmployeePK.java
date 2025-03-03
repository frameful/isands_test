package ru.isands.test.estore.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class ElectroEmployeePK implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long employeeId;

    private Long electroTypeId;

}
