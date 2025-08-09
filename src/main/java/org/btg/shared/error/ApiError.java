package org.btg.shared.error;

public class ApiError extends BaseError {
  public ApiError(Exception e) {
    super(e, "Internal Server Error");
  }
}
