package org.btg.app.util;

import java.util.Optional;

import org.btg.shared.error.ApiError;
import org.btg.shared.error.BadRequestError;
import org.btg.shared.error.InternalError;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUtil<T, S> {
  public Optional<T> data;
  public Optional<S> error;
  public Optional<Meta> meta;

  @JsonIgnore
  public int status;

  @Data
  public static class Meta {
    private int limit;
    private int offset;
    private int total;
  }

  public static enum StatusUtil {
    SUCCESS(200),
    CREATED(201),
    NO_CONTENT(204),
    BAD_REQUEST(400),
    SERVER_ERROR(500);

    private int status;

    StatusUtil(int status) {
      this.status = status;
    }

    public int get() {
      return this.status;
    }
  }

  public static class ErrorUtil {
    public static ResponseError getError(Exception e) {
      String serverError = "Internal Server Error";
      return switch (e) {
        case ApiError a -> new ResponseError(StatusUtil.SERVER_ERROR.get(), new ErrorMessage(serverError, null));
        case BadRequestError b ->
          new ResponseError(StatusUtil.BAD_REQUEST.get(), new ErrorMessage(b.getMessage(), b.getValues()));
        case InternalError i -> new ResponseError(StatusUtil.SERVER_ERROR.get(), new ErrorMessage(serverError, null));
        default -> DefaultError(e);
      };

    }
  }

  protected static ResponseError DefaultError(Exception e) {
    var i = new InternalError(e);
    return new ResponseError(StatusUtil.SERVER_ERROR.get(), new ErrorMessage(i.getMessage(), null));
  }

  public record ResponseError(int status, ErrorMessage data) {
  }

  @Data
  @RequiredArgsConstructor
  @JsonInclude(JsonInclude.Include.NON_NULL)
  public static class ErrorMessage {
    private final String message;
    private final Object data;
  }

}
