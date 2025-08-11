package org.btg.shared.error;

import lombok.Getter;

public class ConstraintError extends BaseError {
  @Getter
  private String value;

  public ConstraintError(Exception e, String value) {
    super(e, "BadRequest - Already exists code order");
  }
}
