package ru.isands.test.estore.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import ru.isands.test.estore.model.ElectroItem;
import ru.isands.test.estore.model.Purchase;

import javax.transaction.Transactional;

public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, Long> {

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

	List<Purchase> findByShop(@Param("shopId") Long shopId);

}
