package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.ElectroItem;
import ru.isands.test.estore.model.ElectroShop;
import ru.isands.test.estore.model.ElectroShopPK;
import ru.isands.test.estore.model.Shop;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.Set;

public interface ElectroShopRepository extends JpaRepository<ElectroShop, ElectroShopPK> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_eshop (shop_id, electro_item_id, count) VALUES (:shopId, :electroItemId, :count)", nativeQuery = true)
    void insertElectroShop(@Param("shopId") Long shopId, @Param("electroItemId") Long electroItemId, @Param("count") Long count);

    Optional<ElectroShop> findByShopAndElectroItem(Shop shop, ElectroItem electroItem);

    Set<ElectroShop> findByElectroItem(ElectroItem electroItem);
}
