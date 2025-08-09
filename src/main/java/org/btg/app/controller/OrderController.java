package org.btg.app.controller;

import org.btg.app.dto.input.OrderInputDto;
import org.btg.app.usecase.OrderUsecase;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("order")
@Produces(MediaType.APPLICATION_JSON)
public class OrderController {

  @Inject
  OrderUsecase usecase;

  @GET
  @Path("client/{cod}")
  public Response getOrderByCodClient(@PathParam("cod") int cod) {
    return Response.ok().build();
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
