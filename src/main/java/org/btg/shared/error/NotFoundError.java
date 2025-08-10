package org.btg.shared.error;

import java.util.List;

import lombok.Getter;

public class NotFoundError extends BaseError {

  @Getter
  private List<String> values;

  public NotFoundError(Exception e, String msg) {
    super(e, msg);
  }
}
