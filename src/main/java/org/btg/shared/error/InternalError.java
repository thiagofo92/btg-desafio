package org.btg.shared.error;

public class InternalError extends BaseError {
  public InternalError(Exception e) {
    super(e, "Internal Server Error");
  }
}
