package org.btg.core.entity;

import java.math.BigDecimal;
import java.util.List;

import lombok.Data;

@Data
public class OrderEntity {

  private Integer codOrder;
  private Integer codClient;
  private List<OrderItem> items;

  @Data
  public static class OrderItem {
    private String product;
    private int total;
    private BigDecimal price;
  }

}
