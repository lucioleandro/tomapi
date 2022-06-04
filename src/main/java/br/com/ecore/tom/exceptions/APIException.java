package br.com.ecore.tom.exceptions;

public class APIException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Long code;

  public APIException(Long code) {
    super();
    this.code = code;
  }

  public APIException(String message, Throwable cause, Long code) {
    super(message, cause);
    this.code = code;
  }

  public APIException(String message, Long code) {
    super(message);
    this.code = code;
  }

  public APIException(Throwable cause, Long code) {
    super(cause);
    this.code = code;
  }

  public Long getCode() {
    return this.code;
  }
}
