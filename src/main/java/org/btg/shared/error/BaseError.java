package org.btg.shared.error;

import org.jboss.logging.Logger;

public abstract class BaseError extends Exception {
  protected Logger LOG = Logger.getLogger(this.getClass());

  public BaseError(Exception e, String msg) {
    super(msg);

    LOG.error(e.getMessage());
    LOG.error(e.getCause());

    for (var el : e.getStackTrace()) {
      LOG.error(el);
    }
  }

}
