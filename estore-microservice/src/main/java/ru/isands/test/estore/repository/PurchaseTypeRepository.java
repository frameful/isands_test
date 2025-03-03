package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.PurchaseType;

import javax.transaction.Transactional;

public interface PurchaseTypeRepository extends JpaRepository<PurchaseType, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_purchase_type (id, name) VALUES (:purchaseTypeId, :name)", nativeQuery = true)
    void insertPurchaseType(@Param("purchaseTypeId") Long purchaseTypeId, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
    void updateCurrentId(@Param("counterName") String counterName);
}
