package org.btg.infra.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import org.btg.core.entity.OrderEntity.OrderItem;
import org.btg.infra.port.repository.OrderRepositoryPort;
import org.btg.shared.error.DbError;

import io.agroal.api.AgroalDataSource;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class OrderRepository implements OrderRepositoryPort {

  @Inject
  AgroalDataSource connectionPool;

  @Override
  public void createOrder(int order, int codClient) throws DbError {
    String sql = """
        insert into btg.order (id, id_client) values (?,?)
            """;

    try (Connection conn = connectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, order);
      ps.setInt(2, codClient);

      ps.executeUpdate();
    } catch (Exception e) {
      throw new DbError(e);
    }
  }

  @Override
  public void insertOrderItems(int order, List<OrderItem> entities) throws DbError {
    String sql = """
        insert into btg.item (id_order, product_name, price, total) values (?,?,?,?)
        """;

    try (Connection conn = connectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      for (var item : entities) {

        ps.setInt(1, order);
        ps.setString(2, item.getProduct());
        ps.setBigDecimal(3, item.getPrice());
        ps.setInt(4, item.getTotal());

        ps.addBatch();
      }

      ps.executeBatch();
    } catch (Exception e) {
      throw new DbError(e);
    }
  }

  @Override
  public List<?> getOrderByCodClient(int cod) throws DbError {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOrderByCodClient'");
  }

  @Override
  public List<?> getOrderByCodOrder(int cod) throws DbError {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOrderByCodOrder'");
  }

  @Override
  public List<?> getTotalOrderByCodClient(int cod) throws DbError {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getTotalOrderByCodClient'");
  }
}
