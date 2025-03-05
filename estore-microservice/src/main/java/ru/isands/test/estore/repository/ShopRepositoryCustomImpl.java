package ru.isands.test.estore.repository;

import ru.isands.test.estore.model.dto.BestEmployeeDto;
import ru.isands.test.estore.model.dto.BestShopDto;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.swing.*;

public class ShopRepositoryCustomImpl implements ShopRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public BestShopDto findLargestTotalPriceOfItemsSoldByCash() {

          String sql = "SELECT s.id, s.name, s.address, SUM(eitem.price) as total_sum " +
                  "FROM store_purchase p " +
                  "JOIN store_electro_item eitem ON p.electro_id = eitem.id " +
                  "JOIN store_purchase_type ptype ON p.type_id = ptype.id " +
                  "JOIN store_shop s ON p.shop_id = s.id " +
                  "WHERE ptype.name = 'Наличные' " +
                  "GROUP BY s.id " +
                  "ORDER BY total_sum DESC";

        Query query = entityManager.createNativeQuery(sql);
        Object[] result = (Object[]) query.setMaxResults(1).getSingleResult();

        return new BestShopDto(
                ((Number) result[0]).longValue(), // id
                (String) result[1],              // firstName
                (String) result[2],              // lastName
                ((Number) result[3]).longValue() // totalItemsSold
        );
      }

}
