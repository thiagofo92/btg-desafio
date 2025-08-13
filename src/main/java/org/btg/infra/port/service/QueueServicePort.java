package org.btg.infra.port.service;

import org.btg.app.dto.input.OrderInputDto;
import org.btg.infra.dto.output.OrderOutputDto;
import org.btg.shared.error.QueueError;

public interface QueueServicePort {
  OrderOutputDto getDataFromQueue() throws QueueError;

  void emitte(OrderInputDto input) throws QueueError;

}
