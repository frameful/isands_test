package ru.isands.test.estore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
@Table(name = "store_electro_type")
public class ElectroType {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "electro_type_counter")
    @TableGenerator(name = "electro_type_counter", pkColumnName = "name",
            pkColumnValue = "ru.isands.test.estore.entity.ElectroType",
            table = "counter", valueColumnName = "currentid",
            allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

}
