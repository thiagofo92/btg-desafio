package org.btg.infra.service;

import java.nio.charset.StandardCharsets;

import org.btg.app.dto.input.OrderInputDto;
import org.btg.infra.dto.output.OrderOutputDto;
import org.btg.infra.port.service.QueueServicePort;
import org.btg.shared.error.QueueError;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RabbitMQService implements QueueServicePort {
  @Channel("btg-orders-out")
  Emitter<byte[]> emitter;

  @Inject
  ObjectMapper mapper;

  @Override
  public OrderOutputDto getDataFromQueue() throws QueueError {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getDataFromQueue'");
  }

  @Override
  public void emitte(OrderInputDto input) throws QueueError {
    try {
      String json = mapper.writeValueAsString(input);
      this.emitter.send(json.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      throw new QueueError(e);
    }
  }

}
