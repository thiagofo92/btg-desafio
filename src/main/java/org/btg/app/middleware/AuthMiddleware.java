package org.btg.app.middleware;

import java.io.IOException;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import jakarta.annotation.Priority;
import jakarta.ws.rs.Priorities;
import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;

@Provider
@Priority(Priorities.AUTHENTICATION)
public class AuthMiddleware implements ContainerRequestFilter {

  @ConfigProperty(name = "middleware.token")
  private String token;

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

    if (!auth.contains(this.token)) {
      ctx.abortWith(Response
          .status(Response.Status.UNAUTHORIZED)
          .entity("Invalid Token")
          .build());
      return;
    }

  }

}
