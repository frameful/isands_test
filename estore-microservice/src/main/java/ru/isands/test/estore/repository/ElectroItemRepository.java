package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.ElectroItem;

import javax.transaction.Transactional;

public interface ElectroItemRepository extends PagingAndSortingRepository<ElectroItem, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_electro_item (id, name, electro_type_id, price, count, archive, description) " +
            "VALUES (:electroItemId, :name, :electroTypeId, :price, :count, :acrhive, :description)", nativeQuery = true)
    void insertElectroItem(@Param("electroItemId") Long electroItemId, @Param("name") String name,
                               @Param("electroTypeId") Long electroTypeId, @Param("price") Long price,
                               @Param("count") Long count, @Param("acrhive") Boolean archive,
                               @Param("description") String description);

    @Modifying
    @Transactional
    @Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
    void updateCurrentId(@Param("counterName") String counterName);

    boolean existsByName(String name);
}
