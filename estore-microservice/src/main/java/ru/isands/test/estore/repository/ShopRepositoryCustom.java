package ru.isands.test.estore.repository;

import ru.isands.test.estore.model.dto.BestShopDto;

public interface ShopRepositoryCustom {

    BestShopDto findLargestTotalPriceOfItemsSoldByCash();

}
