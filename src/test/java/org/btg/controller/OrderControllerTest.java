package org.btg.controller;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.btg.app.controller.OrderController;
import org.btg.app.dto.input.OrderInputDto;
import org.btg.app.dto.input.OrderInputDto.OrderItem;
import org.btg.app.dto.output.OrderOutputDto;
import org.btg.app.util.ResponseUtil;
import org.btg.app.util.ResponseUtil.ErrorMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.junit.jupiter.api.Test;

import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;

@QuarkusTest
@TestHTTPEndpoint(OrderController.class)
public class OrderControllerTest {
  @ConfigProperty(name = "middleware.token")
  private String token;

  @Test
  void testGetOrderByCodClient() {
    var rs = given()
        .header("Authorization", token)
        .when()
        .get("clients/" + 32)
        .then()
        .statusCode(200)
        .extract()
        .as(new TypeRef<ResponseUtil<List<OrderOutputDto.ItemDto>, ErrorMessage>>() {
        });

    assertEquals(false, rs.error.isPresent());
    System.out.println(rs.data.get());
    assertEquals(2, rs.data.get().size());

  }

  @Test
  void testGetTotalOrderByCodClient() {
    var rs = given()
        .header("Authorization", token)
        .when()
        .get(String.format("clients/%d/total", 32))
        .then()
        .statusCode(200)
        .extract()
        .as(new TypeRef<ResponseUtil<OrderOutputDto.TotalOrder, ErrorMessage>>() {
        });

    assertEquals(false, rs.error.isPresent());
    assertEquals(1, rs.data.get().getTotal());
  }

  @Test
  void testGetTotalPriceOrderByCodOrder() {
    var rs = given()
        .header("Authorization", token)
        .when()
        .get(String.format("%d/price/total", 100))
        .then()
        .statusCode(200)
        .extract()
        .as(new TypeRef<ResponseUtil<OrderOutputDto.TotalOrderPrice, ErrorMessage>>() {
        });

    assertEquals(false, rs.error.isPresent());
    assertEquals(new BigDecimal("7000.00"), rs.data.get().getTotalPrice());
  }

  @Test
  void testCreate() {
    OrderInputDto dto = new OrderInputDto();
    List<OrderInputDto.OrderItem> items = new ArrayList<>();
    OrderItem item = new OrderItem();

    dto.setCodClient(20);
    dto.setCodOrder(500);
    dto.setItems(items);
    items.add(item);
    item.setPrice(new BigDecimal("1000.00"));
    item.setProduct("TV");
    item.setTotal(1);

    given()
        .header("Authorization", token)
        .contentType("application/json")
        .body(dto)
        .when()
        .post()
        .then()
        .statusCode(201);

    var rs = given()
        .header("Authorization", token)
        .when()
        .get("clients/" + 20)
        .then()
        .statusCode(200)
        .extract()
        .as(new TypeRef<ResponseUtil<List<OrderOutputDto.ItemDto>, ErrorMessage>>() {
        });

    assertEquals(false, rs.error.isPresent());
    System.out.println(rs.data.get());
    assertEquals(1, rs.data.get().size());

  }

}
