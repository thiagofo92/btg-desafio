package org.btg.app.usecase;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.btg.app.dto.input.OrderInputDto;
import org.btg.app.dto.output.OrderOutputDto;
import org.btg.app.util.ResponseUtil;
import org.btg.app.util.ResponseUtil.ErrorMessage;
import org.btg.app.util.ResponseUtil.ErrorUtil;
import org.btg.app.util.ResponseUtil.Meta;
import org.btg.app.util.ResponseUtil.StatusUtil;
import org.btg.core.entity.OrderEntity;
import org.btg.infra.port.repository.OrderRepositoryPort;
import org.btg.infra.port.service.QueueServicePort;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@ApplicationScoped
@RequiredArgsConstructor
public class OrderUsecase {
  private final OrderRepositoryPort repository;
  private final QueueServicePort service;

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

  public ResponseUtil<List<OrderOutputDto.ItemDto>, ErrorMessage> getOrderByCodClient(int cod, int limit, int page) {
    ResponseUtil.ResponseUtilBuilder<List<OrderOutputDto.ItemDto>, ErrorMessage> result = ResponseUtil.builder();
    try {

      var output = this.repository.getOrderByCodClient(cod, limit, page);

      if (page == 1) {
        Long total = this.repository.getCountOrderByCodClient(cod);
        Meta meta = new Meta(limit, page, total);
        result.meta(Optional.of(meta));
      }

      result.status(StatusUtil.SUCCESS.get());
      result.data(Optional.of(output));

    } catch (Exception e) {
      var err = ErrorUtil.getError(e);
      result.status(err.status());
      result.error(Optional.of(err.data()));
    }

    return result.build();
  }

  public ResponseUtil<OrderOutputDto.TotalOrder, ErrorMessage> getTotalOrderByCodClient(int cod) {
    ResponseUtil.ResponseUtilBuilder<OrderOutputDto.TotalOrder, ErrorMessage> result = ResponseUtil.builder();
    try {

      var output = this.repository.getTotalOrderByCodClient(cod);

      result.status(StatusUtil.SUCCESS.get());
      result.data(Optional.of(output));

    } catch (Exception e) {
      var err = ErrorUtil.getError(e);
      result.status(err.status());
      result.error(Optional.of(err.data()));
    }

    return result.build();
  }

  public ResponseUtil<OrderOutputDto.TotalOrderPrice, ErrorMessage> getTotalPriceOrderByCodOrder(int cod) {
    ResponseUtil.ResponseUtilBuilder<OrderOutputDto.TotalOrderPrice, ErrorMessage> result = ResponseUtil.builder();
    try {

      var output = this.repository.getTotalPriceOrderByCodOrder(cod);

      result.status(StatusUtil.SUCCESS.get());
      result.data(Optional.of(output));

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

  public ResponseUtil<Void, ErrorMessage> emitte(OrderInputDto input) {
    ResponseUtil.ResponseUtilBuilder<Void, ErrorMessage> result = ResponseUtil.builder();
    try {
      this.service.emitte(input);
      result.status(ResponseUtil.StatusUtil.ACCEPTED.get());
    } catch (Exception e) {
      var err = ErrorUtil.getError(e);
      result.status(err.status());
      result.error(Optional.of(err.data()));
    }

    return result.build();
  }

}
