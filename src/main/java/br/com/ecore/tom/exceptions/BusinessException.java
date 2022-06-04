package br.com.ecore.tom.exceptions;

public class BusinessException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Long code;

  public BusinessException(Long code) {
    super();
    this.code = code;
  }

  public BusinessException(String message, Throwable cause, Long code) {
    super(message, cause);
    this.code = code;
  }

  public BusinessException(String message, Long code) {
    super(message);
    this.code = code;
  }

  public BusinessException(Throwable cause, Long code) {
    super(cause);
    this.code = code;
  }

  public Long getCode() {
    return this.code;
  }

}
