
package org.btg.infra.port.repository;

import java.util.List;

import org.btg.app.dto.output.OrderOutputDto;
import org.btg.app.dto.output.OrderOutputDto.TotalOrderPrice;
import org.btg.core.entity.OrderEntity;
import org.btg.shared.error.ConstraintError;
import org.btg.shared.error.DbError;
import org.btg.shared.error.NotFoundError;

public interface OrderRepositoryPort {
  void createOrder(int order, int codClient) throws DbError, ConstraintError;

  void insertOrderItems(int order, List<OrderEntity.OrderItem> entities) throws DbError;

  /**
   * @param cod
   * @param limit
   * @param page
   * @return
   * @throws DbError
   */
  List<OrderOutputDto.ItemDto> getOrderByCodClient(int cod, int limit, int page)
      throws DbError, NotFoundError, Exception;

  /**
   * @param cod
   * @param limit
   * @param page
   * @return
   * @throws DbError
   */
  Long getCountOrderByCodClient(int cod) throws DbError;

  /**
   * Listar a quantidade de pedidos por cliente
   * 
   * @param cod
   * @return Long
   * @throws DbError
   */
  OrderOutputDto.TotalOrder getTotalOrderByCodClient(int cod) throws DbError, NotFoundError, Exception;

  /**
   * Listar o valor total dos pedidos usando o código do pedido
   * 
   * @param cod
   * @return BigDecimal
   * @throws DbError
   */
  TotalOrderPrice getTotalPriceOrderByCodOrder(int cod) throws DbError, NotFoundError, Exception;

}
