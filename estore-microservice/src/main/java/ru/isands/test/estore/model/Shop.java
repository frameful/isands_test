package ru.isands.test.estore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "store_shop")
public class Shop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "shop_counter")
    @TableGenerator(name = "shop_counter", pkColumnName = "name", pkColumnValue = "ru.isands.test.estore.entity.Shop", table = "counter", valueColumnName = "currentid", allocationSize = 1)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @OneToMany(mappedBy = "shop", fetch = FetchType.EAGER)
    private Set<Employee> employees;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "store_eshop",
            joinColumns = @JoinColumn(name = "shop_id"),
            inverseJoinColumns = @JoinColumn(name = "electro_item_id")
    )
    private Set<ElectroItem> electroItems;

}
