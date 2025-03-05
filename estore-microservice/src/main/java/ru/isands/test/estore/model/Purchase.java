package ru.isands.test.estore.model;


import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "store_purchase")
public class Purchase implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "purchase_counter")
	@TableGenerator(name = "purchase_counter", pkColumnName = "name",
			pkColumnValue = "ru.isands.test.estore.entity.Purchase",
			table = "counter", valueColumnName = "currentid",
			allocationSize = 1)
	@Column(name = "id", unique = true, nullable = false)
	Long id;

	@Column(name = "purchaseDate", nullable = false)
	Date purchaseDate;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "employee_id")
	private Employee employee;

	@ManyToOne
	@JoinColumn(name = "electro_id")
	private ElectroItem electroItem;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "type_id")
	private PurchaseType purchaseType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "shop_id")
	private Shop shop;

}