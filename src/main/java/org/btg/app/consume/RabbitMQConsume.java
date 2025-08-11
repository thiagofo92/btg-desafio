package org.btg.app.consume;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.CompletionStage;

import org.btg.app.dto.input.OrderInputDto;
import org.btg.app.usecase.OrderUsecase;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Message;
import org.jboss.logging.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.smallrye.reactive.messaging.annotations.Blocking;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class RabbitMQConsume {
  private static Logger LOG = Logger.getLogger(RabbitMQConsume.class);

  @Inject
  OrderUsecase usecase;

  @Incoming("btg-orders")
  @Blocking
  public CompletionStage<Void> consume(Message<byte[]> message) throws InterruptedException {
    try {
      ObjectMapper mapper = new ObjectMapper();
      String json = new String(message.getPayload(), StandardCharsets.UTF_8);
      OrderInputDto dto = mapper.readValue(json, OrderInputDto.class);
      var result = this.usecase.create(dto);

      if (result.error.isPresent()) {
        return message.nack(new Exception(result.error.get().getMessage()));
      }

      return message.ack();
    } catch (Exception e) {
      LOG.error(e.getMessage());
      for (var el : e.getStackTrace()) {
        LOG.error(el);
      }
      return message.nack(e);
    }

  }
}
