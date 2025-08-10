package org.btg.app.controller;

import org.btg.app.dto.input.OrderInputDto;
import org.btg.app.usecase.OrderUsecase;

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

@Path("order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

  @Inject
  OrderUsecase usecase;

  @GET
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
  @Path("clients/{cod_client}/total")
  public Response getTotalOrderByCodClient(@PathParam("cod_client") int cod) {

    var result = this.usecase.getTotalOrderByCodClient(cod);
    return Response
        .status(result.status)
        .entity(result)
        .build();
  }

  @GET
  @Path("{cod_order}/price/total")
  public Response getTotalPriceOrderByCodOrder(@PathParam("cod_order") int cod) {

    var result = this.usecase.getTotalPriceOrderByCodOrder(cod);
    return Response
        .status(result.status)
        .entity(result)
        .build();
  }

  @POST
  public Response create(OrderInputDto input) {
    var result = this.usecase.create(input);
    return Response
        .status(result.status)
        .entity(result)
        .build();
  }

}
