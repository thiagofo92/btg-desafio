package org.btg.shared.error;

import java.util.List;

import lombok.Getter;

public class BadRequestError extends BaseError {

  @Getter
  private List<String> values;

  public BadRequestError(Exception e, List<String> values) {
    super(e, e.getMessage());
  }
}
