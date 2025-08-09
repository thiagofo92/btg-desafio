package org.btg.app.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.btg.app.dto.input.OrderInputDto;
import org.btg.app.util.ResponseUtil;
import org.btg.app.util.ResponseUtil.ErrorMessage;
import org.btg.app.util.ResponseUtil.ErrorUtil;
import org.btg.app.util.ResponseUtil.StatusUtil;
import org.btg.core.entity.OrderEntity;
import org.btg.infra.port.repository.OrderRepositoryPort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderUsecase {
  private final OrderRepositoryPort repository;

  @Transactional
  public ResponseUtil<Void, ErrorMessage> create(OrderInputDto input) {
    ResponseUtil.ResponseUtilBuilder<Void, ErrorMessage> result = ResponseUtil.builder();
    try {
      var entities = this.mapperItem(input.getItems());
      this.repository.createOrder(input.getCodOrder(), input.getCodClient());
      this.repository.insertOrderItems(input.getCodOrder(), entities);

      result.status(StatusUtil.CREATED.get());

    } catch (Exception e) {
      var err = ErrorUtil.getError(e);
      result.status(err.status());
      result.error(Optional.of(err.data()));
    }

    return result.build();
  }

  private List<OrderEntity.OrderItem> mapperItem(List<OrderInputDto.OrderItem> dto) {

    List<OrderEntity.OrderItem> entities = new ArrayList<>();

    for (var item : dto) {

      var o = new OrderEntity.OrderItem();
      o.setProduct(item.getProduct());
      o.setPrice(item.getPrice());
      o.setTotal(item.getTotal());

      entities.add(o);
    }

    return entities;

  }

}
