
package org.btg.infra.port.repository;

import java.util.List;

import org.btg.core.entity.OrderEntity;
import org.btg.shared.error.DbError;

public interface OrderRepositoryPort {
  void createOrder(int order, int codClient) throws DbError;

  void insertOrderItems(int order, List<OrderEntity.OrderItem> entities) throws DbError;

  List<?> getOrderByCodClient(int cod) throws DbError;

  List<?> getOrderByCodOrder(int cod) throws DbError;

  List<?> getTotalOrderByCodClient(int cod) throws DbError;

}
