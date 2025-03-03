package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.PositionType;

import javax.transaction.Transactional;

public interface PositionTypeRepository extends JpaRepository<PositionType, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_position_type (id, name) VALUES (:positionTypeId, :name)", nativeQuery = true)
    void insertPositionType(@Param("positionTypeId") Long positionTypeId, @Param("name") String name);

    @Modifying
    @Transactional
    @Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
    void updateCurrentId(@Param("counterName") String counterName);

}
