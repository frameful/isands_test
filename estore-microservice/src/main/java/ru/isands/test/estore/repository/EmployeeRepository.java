package ru.isands.test.estore.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import ru.isands.test.estore.model.Employee;

import javax.transaction.Transactional;

public interface EmployeeRepository extends PagingAndSortingRepository<Employee, Long> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO store_employee" +
            " (id, lastname, firstname, patronymic, birth_date, position_id, shop_id, gender) " +
            "VALUES" +
            " (:employeeId, :lastname, :firstname, :patronymic, :birthDate, :positionId, :shopId, :gender)",
            nativeQuery = true)
    void insertEmployee(@Param("employeeId") Long employeeId, @Param("lastname") String lastname,
                        @Param("firstname") String firstname, @Param("patronymic") String patronymic,
                        @Param("birthDate") Date birthDate, @Param("positionId") Long positionId,
                        @Param("shopId") Long shopId, @Param("gender") Boolean gender);
    List<Employee> findByShop_Id(Long shopId);

    @Modifying
    @Transactional
    @Query(value = "UPDATE counter SET currentid = currentid + 1 WHERE name = :counterName", nativeQuery = true)
    void updateCurrentId(@Param("counterName") String counterName);

    @Query(value = "SELECT e.*," +
            "SUM(p.employee_id) AS total_items_sold " +
            "FROM store_employee e" +
            "JOIN store_purchase p ON e.id = p.employee_id" +
            "WHERE p.purchase_date >= DATEADD(YEAR, -1, GETDATE())" +
            "GROUP BY e.employee_id" +
            "ORDER BY total_items_sold DESC" +
            "LIMIT 1;")
    Employee findBestByNumberOfItemsSold();

    @Query(value = "SELECT e.*," +
            "SUM(eitem.price) AS total_items_sold " +
            "FROM store_employee e" +
            "JOIN store_purchase p ON e.id = p.employee_id" +
            "JOIN store_electro_item eitem ON eitem.id = p.electro_id" +
            "WHERE p.purchase_date >= DATEADD(YEAR, -1, GETDATE())" +
            "GROUP BY e.employee_id" +
            "ORDER BY total_items_sold DESC" +
            "LIMIT 1;")
    Employee findBestByTotalPriceOfItemsSold();

    @Query(value = "SELECT e.*," +
            "SUM(p.employee_id) AS total_items_sold " +
            "FROM store_employee e" +
            "JOIN store_purchase p ON e.id = p.employee_id" +
            "JOIN store_position_type pos ON e.position_id = pos.id" +
            "JOIN store_electro_item eitem ON p.electro_id = eitem.id" +
            "WHERE p.purchase_date >= DATEADD(YEAR, -1, GETDATE())" +
            "AND pos.name = 'Младший продавец-консультант'" +
            "AND eitem.name = 'Умные часы'" +
            "GROUP BY e.employee_id" +
            "ORDER BY total_items_sold DESC" +
            "LIMIT 1;")
    Employee findBestJuniorConsultantByNumberOfSmartWatchesSold();

}