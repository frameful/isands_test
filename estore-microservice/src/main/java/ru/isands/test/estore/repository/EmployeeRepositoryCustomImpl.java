package ru.isands.test.estore.repository;

import ru.isands.test.estore.model.dto.BestEmployeeDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.Arrays;

public class EmployeeRepositoryCustomImpl implements EmployeeRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BestEmployeeDto findBestByTotalPriceOfItemsSold() {

        String sql = "SELECT e.id, e.firstName, e.lastName, SUM(eitem.price) as total_items_sold " +
                "FROM store_employee e " +
                "JOIN store_purchase p ON p.employee_id = e.id " +
                "JOIN store_position_type pos ON pos.id = e.position_id " +
                "JOIN store_electro_item eitem ON eitem.id = p.electro_id  " +
                "WHERE p.purchase_date >= CURRENT_DATE - 365 " +
                "GROUP BY e.id, e.firstName, e.lastName " +
                "ORDER BY total_items_sold DESC ";

        Query query = entityManager.createNativeQuery(sql);
        Object[] result = (Object[]) query.setMaxResults(1).getSingleResult();

        return new BestEmployeeDto(
                ((Number) result[0]).longValue(), // id
                (String) result[1],              // firstName
                (String) result[2],              // lastName
                ((Number) result[3]).longValue() // totalItemsSold
        );
    }

    @Override
    public BestEmployeeDto findBestByNumberOfItemsSold() {

        String sql = "SELECT e.id, e.firstName, e.lastName, COUNT(e.id) as total_items_sold " +
                "FROM store_employee e " +
                "JOIN store_purchase p ON p.employee_id = e.id " +
                "WHERE p.purchase_date >= CURRENT_DATE - 365 " +
                "GROUP BY e.id " +
                "ORDER BY total_items_sold DESC ";

        Query query = entityManager.createNativeQuery(sql);
        Object[] result = (Object[]) query.setMaxResults(1).getSingleResult();
        System.out.println(Arrays.toString(result));

        return new BestEmployeeDto(
                ((Number) result[0]).longValue(), // id
                (String) result[1],              // firstName
                (String) result[2],              // lastName
                ((Number) result[3]).longValue() // totalItemsSold
        );
    }

    @Override
    public BestEmployeeDto findBestJuniorConsultantByNumberOfSmartWatchesSold() {

        String sql = "SELECT e.id, e.firstName, e.lastName, COUNT(e.id) as total_items_sold " +
                "FROM store_employee e " +
                "JOIN store_purchase p ON p.employee_id = e.id " +
                "JOIN store_shop s ON p.shop_id = s.id " +
                "JOIN store_position_type pos ON pos.id = e.position_id " +
                "JOIN store_electro_item eitem ON eitem.id = p.electro_id " +
                "JOIN store_electro_type etype ON eitem.electro_type_id = etype.id " +
                "WHERE p.purchase_date >= CURRENT_DATE - 365 " +
                "AND pos.name = 'Младший продавец-консультант' " +
                "AND eitem.name = 'Умные часы' " +
                "GROUP BY e.id, e.firstName, e.lastName " +
                "ORDER BY total_items_sold DESC ";

        Query query = entityManager.createNativeQuery(sql);
        Object[] result = (Object[]) query.setMaxResults(1).getSingleResult();

        return new BestEmployeeDto(
                ((Number) result[0]).longValue(), // id
                (String) result[1],              // firstName
                (String) result[2],              // lastName
                ((Number) result[3]).longValue() // totalItemsSold
        );
    }

}
