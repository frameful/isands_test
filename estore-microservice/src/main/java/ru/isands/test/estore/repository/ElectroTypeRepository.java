package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.ElectroType;

import javax.transaction.Transactional;

public interface ElectroTypeRepository extends JpaRepository<ElectroType, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_electro_type (id, name) VALUES (:electroTypeId, :name)", nativeQuery = true)
    void insertElectroType(@Param("electroTypeId") Long electroTypeId, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
    void updateCurrentId(@Param("counterName") String counterName);

}
