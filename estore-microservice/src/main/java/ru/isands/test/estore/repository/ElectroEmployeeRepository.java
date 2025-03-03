package ru.isands.test.estore.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.ElectroEmployee;
import ru.isands.test.estore.model.ElectroEmployeePK;

import javax.transaction.Transactional;

public interface ElectroEmployeeRepository extends JpaRepository<ElectroEmployee, ElectroEmployeePK> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_eemployee (employee_id, electro_type_id) VALUES (:employeeId, :electroTypeId)", nativeQuery = true)
    void insertElectroEmployee(@Param("employeeId") Long employeeId, @Param("electroTypeId") Long electroTypeId);

}
