package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.Shop;

import javax.transaction.Transactional;

public interface ShopRepository extends JpaRepository<Shop, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_shop (id, name, address) VALUES (:shopId, :name, :address)", nativeQuery = true)
    void insertShop(@Param("shopId") Long shopId, @Param("name") String name, @Param("address") String address);

    @Modifying
    @Transactional
    @Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
    void updateCurrentId(@Param("counterName") String counterName);
}
