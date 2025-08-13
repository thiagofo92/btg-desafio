package org.btg.app.middleware;

import java.io.IOException;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthMiddleware implements ContainerRequestFilter {

  @Override
  public void filter(ContainerRequestContext ctx) throws IOException {
    String auth = ctx.getHeaderString("Authorization");

    if (auth == null || auth.isBlank()) {
      ctx.abortWith(Response
          .status(Response.Status.UNAUTHORIZED)
          .entity("Missing Token")
          .build());
      return;
    }

    if (!auth.contains("asdf1234023")) {
      ctx.abortWith(Response
          .status(Response.Status.UNAUTHORIZED)
          .entity("Invalid Token")
          .build());
      return;
    }

  }

}
