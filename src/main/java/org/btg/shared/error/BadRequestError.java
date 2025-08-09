package org.btg.shared.error;

import java.util.List;

import lombok.Getter;

public class BadRequestError extends BaseError {

  @Getter
  private List<String> values;

  /**
   * @param e      Type Exception
   * @param values Values will be the property and error ex: osis - Value is null
   */
  public BadRequestError(Exception e, List<String> values) {
    super(e, e.getMessage());
  }
}
