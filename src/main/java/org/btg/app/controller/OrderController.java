package org.btg.app.controller;

import org.btg.app.dto.input.OrderInputDto;
import org.btg.app.usecase.OrderUsecase;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import jakarta.inject.Inject;
import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Tag(name = "Pedido")
@Path("order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

  @Inject
  OrderUsecase usecase;

  @GET
  @Operation(summary = "Lista de pedidos realizados por cliente")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "401")
  @APIResponse(responseCode = "404", description = "Cliente não localizado")
  @APIResponse(responseCode = "500")
  @Path("clients/{cod}")
  public Response getOrderByCodClient(@PathParam("cod") int cod,
      @DefaultValue("1000") @QueryParam("limit") int limit,
      @DefaultValue("1") @QueryParam("page") int page) {

    var result = this.usecase.getOrderByCodClient(cod, limit, page);
    return Response
        .status(result.status)
        .entity(result)
        .build();
  }

  @GET
  @Operation(summary = "Quantidade de Pedidos por Cliente")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "401")
  @APIResponse(responseCode = "404", description = "Cliente não localizado")
  @APIResponse(responseCode = "500")
  @Path("clients/{cod_client}/total")
  public Response getTotalOrderByCodClient(@PathParam("cod_client") int cod) {

    var result = this.usecase.getTotalOrderByCodClient(cod);
    return Response
        .status(result.status)
        .entity(result)
        .build();
  }

  @GET
  @Operation(summary = "Valor total do pedido")
  @APIResponse(responseCode = "200")
  @APIResponse(responseCode = "401")
  @APIResponse(responseCode = "404", description = "Pedido não localizado")
  @APIResponse(responseCode = "500")
  @Path("{cod_order}/price/total")
  public Response getTotalPriceOrderByCodOrder(@PathParam("cod_order") int cod) {

    var result = this.usecase.getTotalPriceOrderByCodOrder(cod);
    return Response
        .status(result.status)
        .entity(result)
        .build();
  }

  @POST
  @Operation(summary = "Cadastrar novo pedido")
  @APIResponse(responseCode = "201")
  @APIResponse(responseCode = "401")
  @APIResponse(responseCode = "400", description = "Pedido já cadastrado")
  @APIResponse(responseCode = "500")
  public Response create(OrderInputDto input) {
    var result = this.usecase.create(input);
    return Response
        .status(result.status)
        .entity(result)
        .build();
  }

  @POST
  @Path("queue")
  @Operation(summary = "Enviar novo pedido para a Fila (RabbitMQ)")
  @APIResponse(responseCode = "202")
  @APIResponse(responseCode = "401")
  @APIResponse(responseCode = "500")
  public Response emitte(OrderInputDto input) {
    var result = this.usecase.emitte(input);
    return Response
        .status(result.status)
        .entity(result)
        .build();
  }

}
