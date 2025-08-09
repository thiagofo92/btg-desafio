package org.btg.shared.error;

public class DbError extends BaseError {
  public DbError(Exception e) {
    super(e, "Internal Server Error");
  }
}
