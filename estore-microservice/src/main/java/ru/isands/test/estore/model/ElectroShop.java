package ru.isands.test.estore.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "store_eshop")
public class ElectroShop {

	@EmbeddedId
	private ElectroShopPK id;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("shop_id")
	@JoinColumn(name = "shop_id")
	private Shop shop;

	@ManyToOne(fetch = FetchType.EAGER)
	@MapsId("electro_item_id")
	@JoinColumn(name = "electro_item_id")
	private ElectroItem electroItem;

	@Column(name = "count", nullable = false)
	private Long count;

}
