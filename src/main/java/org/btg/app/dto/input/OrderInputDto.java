package org.btg.app.dto.input;

import java.math.BigDecimal;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderInputDto {

  @JsonProperty("codigoPedido")
  private Integer codOrder;
  @JsonProperty("codigoCliente")
  private Integer codClient;
  @JsonProperty("itens")
  private List<OrderItem> items;

  @Data
  public static class OrderItem {
    @JsonProperty("produto")
    private String product;
    @JsonProperty("quantidade")
    private int total;
    @JsonProperty("preco")
    private BigDecimal price;
  }

}
