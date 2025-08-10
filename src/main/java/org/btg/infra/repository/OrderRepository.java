package org.btg.infra.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.btg.app.dto.output.OrderOutputDto;
import org.btg.app.dto.output.OrderOutputDto.ItemDto;
import org.btg.app.dto.output.OrderOutputDto.TotalOrder;
import org.btg.app.dto.output.OrderOutputDto.TotalOrderPrice;
import org.btg.core.entity.OrderEntity.OrderItem;
import org.btg.infra.port.repository.OrderRepositoryPort;
import org.btg.shared.error.DbError;
import org.btg.shared.error.NotFoundError;

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
  public List<ItemDto> getOrderByCodClient(int cod, int limit, int page) throws DbError {
    String sql = """
        select o.id, i.product_name, i.total, i.price from btg."order" o
        join btg."item" i on i.id_order = o.id
        where o.id_client = ?
        offset ? limit ?
                """;

    try (Connection conn = connectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {

      ps.setInt(1, cod);
      ps.setInt(2, (page - 1) * limit);
      ps.setInt(3, limit);

      try (ResultSet rs = ps.executeQuery()) {
        List<ItemDto> output = new ArrayList<>();
        while (rs.next()) {
          var dto = new ItemDto();

          dto.setCodOrder(rs.getInt("id"));
          dto.setProduct(rs.getString("product_name"));
          dto.setTotal(rs.getInt("total"));
          dto.setPrice(rs.getBigDecimal("price"));

          output.add(dto);
        }

        return output;
      }

    } catch (Exception e) {
      throw new DbError(e);
    }
  }

  @Override
  public Long getCountOrderByCodClient(int cod) throws DbError {
    String sql = """
        select count(o.id) from btg."order" o
        join btg."item" i on i.id_order = o.id
        where o.id_client = ?
                """;

    try (Connection conn = connectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, cod);

      try (ResultSet rs = ps.executeQuery()) {
        return rs.next() ? rs.getLong(1) : 0;
      }

    } catch (Exception e) {
      throw new DbError(e);
    }
  }

  @Override
  public OrderOutputDto.TotalOrder getTotalOrderByCodClient(int cod) throws DbError, NotFoundError, Exception {
    String sql = """
          select o.id_client, count(o.id) total from btg."order" o
          where o.id_client = ?
          group by 1
        """;

    try (Connection conn = connectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, cod);

      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next())
          throw new NotFoundError(new Exception("Not Found"), "Cliente Not found");

        TotalOrder output = new TotalOrder();
        output.setCodClient(rs.getInt("id_client"));
        output.setTotal(rs.getLong("total"));

        return output;

      }

    } catch (Exception e) {
      if (e instanceof NotFoundError) {
        throw e;
      }

      throw new DbError(e);
    }
  }

  @Override
  public TotalOrderPrice getTotalPriceOrderByCodOrder(int cod) throws DbError, NotFoundError, Exception {
    String sql = """
        select i.id_order, sum(i.price) total_price from btg.item i
        where i.id_order = ?
        group by 1
            """;

    try (Connection conn = connectionPool.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql)) {
      ps.setInt(1, cod);

      try (ResultSet rs = ps.executeQuery()) {
        if (!rs.next())
          throw new NotFoundError(new Exception("Not Found"), "Order Not found");

        System.out.println("HERE");
        var output = new TotalOrderPrice();
        output.setCodOrder(rs.getInt("id_order"));
        output.setTotalPrice(rs.getBigDecimal("total_price"));

        return output;

      }

    } catch (Exception e) {

      if (e instanceof NotFoundError) {
        throw e;
      }

      throw new DbError(e);
    }
  }

}
