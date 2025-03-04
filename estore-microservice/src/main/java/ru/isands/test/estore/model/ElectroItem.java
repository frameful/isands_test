package ru.isands.test.estore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "store_electro_item")
public class ElectroItem {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "electro_item_counter")
    @TableGenerator(name = "electro_item_counter", pkColumnName = "name",
            pkColumnValue = "ru.isands.test.estore.entity.ElectroItem",
            table = "counter", valueColumnName = "currentid",
            allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "count", nullable = false)
    private Long count;

    @Column(name = "archive", nullable = false)
    private Boolean archive;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "electro_type_id")
    private ElectroType electroType;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "store_eshop",
            joinColumns = @JoinColumn(name = "electro_item_id"),
            inverseJoinColumns = @JoinColumn(name = "shop_id")
    )
    private List<Shop> shops;
}
