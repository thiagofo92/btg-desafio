package org.btg.shared.error;

public class ApiAuthError extends BaseError {
  public ApiAuthError(Exception e) {
    super(e, "Internal Server Error");
  }
}
