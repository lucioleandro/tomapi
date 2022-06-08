package br.com.ecore.tom.exceptions;

public class IntegrationException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Long code;

  public IntegrationException(Long code) {
    super();
    this.code = code;
  }

  public IntegrationException(String message, Throwable cause, Long code) {
    super(message, cause);
    this.code = code;
  }

  public IntegrationException(String message, Long code) {
    super(message);
    this.code = code;
  }

  public IntegrationException(Throwable cause, Long code) {
    super(cause);
    this.code = code;
  }

  public Long getCode() {
    return this.code;
  }

}
