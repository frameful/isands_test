package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.Shop;

import javax.transaction.Transactional;

public interface ShopRepository extends PagingAndSortingRepository<Shop, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_shop (id, name, address) VALUES (:shopId, :name, :address)", nativeQuery = true)
    void insertShop(@Param("shopId") Long shopId, @Param("name") String name, @Param("address") String address);

    @Modifying
    @Transactional
    @Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
    void updateCurrentId(@Param("counterName") String counterName);

    @Query(value = "SELECT SUM(eitem.price) " +
            "FROM store_purchase p " +
            "JOIN store_electro_item eitem ON p.electro_id = eitem.id " +
            "JOIN store_purchase_type ptype ON p.type = ptype.id " +
            "JOIN store_shop s ON p.shop_id = shop.id" +
            "WHERE ptype.name = 'Наличные'" +
            "ORDER BY DESC" +
            "LIMIT 1")
    Long findLargestTotalPriceOfItemsSoldByCash(@Param("shopId") Long shopId);

}
