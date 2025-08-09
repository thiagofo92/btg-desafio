package org.btg.shared.error;

public class QueueError extends BaseError {
  public QueueError(Exception e) {
    super(e, "Internal Server Error");
  }
}
