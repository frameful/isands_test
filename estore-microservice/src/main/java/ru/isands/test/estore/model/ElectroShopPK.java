package ru.isands.test.estore.model;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Embeddable
public class ElectroShopPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "shop_id")
	private Long shopId;

	@Column(name = "electro_item_id")
	private Long electroItemId;

}
