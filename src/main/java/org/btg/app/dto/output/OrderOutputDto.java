package org.btg.app.dto.output;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class OrderOutputDto {

  @Data
  public static class ItemDto {
    @JsonProperty("codigoPedido")
    private int codOrder;
    @JsonProperty("produto")
    private String product;
    @JsonProperty("quantidade")
    private int total;
    @JsonProperty("preco")
    private BigDecimal price;
  }

  @Data
  public static class TotalOrder {
    @JsonProperty("codigoCliente")
    private int codClient;
    @JsonProperty("quantidade")
    private Long total;
  }

  @Data
  public static class TotalOrderPrice {
    @JsonProperty("codigoPedido")
    private int codOrder;
    @JsonProperty("precoTotal")
    private BigDecimal totalPrice;
  }

}
