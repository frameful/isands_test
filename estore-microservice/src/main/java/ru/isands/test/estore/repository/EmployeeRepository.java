package ru.isands.test.estore.repository;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.Employee;

import javax.transaction.Transactional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_employee (id, lastname, firstname, patronymic, birth_date, position_id, shop_id, gender) VALUES (:employeeId, :lastname, :firstname, :patronymic, :birthDate, :positionId, :shopId, :gender)", nativeQuery = true)
    void insertEmployee(@Param("employeeId") Long employeeId, @Param("lastname") String lastname,
                        @Param("firstname") String firstname, @Param("patronymic") String patronymic,
                        @Param("birthDate") Date birthDate, @Param("positionId") Long positionId,
                        @Param("shopId") Long shopId, @Param("gender") Boolean gender);

    Set<Employee> findByShop_Id(Long shopId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
    void updateCurrentId(@Param("counterName") String counterName);

}