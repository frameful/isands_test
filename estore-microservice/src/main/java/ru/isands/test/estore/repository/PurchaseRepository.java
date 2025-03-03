package ru.isands.test.estore.repository;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ru.isands.test.estore.model.Purchase;

import javax.transaction.Transactional;

public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO store_purchase (id, electro_id, employee_id, purchase_date, type_id, shop_id) VALUES (:purchaseId, :electroId, :employeeId, :purchaseDate, :typeId, :shopId)", nativeQuery = true)
	void insertPurchase(@Param("purchaseId") Long purchaseId, @Param("electroId") Long electroId,
						@Param("employeeId") Long employeeId, @Param("purchaseDate") Date purchaseDate,
						@Param("typeId") Long typeId, @Param("shopId") Long shopId);

	@Modifying
	@Transactional
	@Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
	void updateCurrentId(@Param("counterName") String counterName);

	Set<Purchase> findByShop(@Param("shopId") Long shopId);

/*	@Query(value = "SELECT * FROM store_purchase ORDER BY purchase_date :sort", nativeQuery = true)
    Set<Purchase> findAllSortByDate(@Param("sort") String sort);*/
}
