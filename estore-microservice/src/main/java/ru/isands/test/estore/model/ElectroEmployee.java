package ru.isands.test.estore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "store_eemployee")
public class ElectroEmployee {

    @EmbeddedId
    private ElectroEmployeePK id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("employeeId")
    @JoinColumn(name = "employee_id")
    private Employee employee;


    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("electroTypeId")
    @JoinColumn(name = "electro_type_id")
    private ElectroType electroType;

}
