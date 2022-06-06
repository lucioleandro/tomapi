package br.com.ecore.tom.handlers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import br.com.ecore.tom.exceptions.APIException;
import br.com.ecore.tom.exceptions.BusinessException;
import br.com.ecore.tom.exceptions.EntityNotFoundException;

@ControllerAdvice
public class HandleExceptionsServiceAdvice {

  private static final Logger logger = LoggerFactory.getLogger(HandleExceptionsServiceAdvice.class);

  @ExceptionHandler({EntityNotFoundException.class})
  public ResponseEntity<String> handleEntityNotFoundException(EntityNotFoundException e) {
    return error(HttpStatus.UNPROCESSABLE_ENTITY, e);
  }

  @ExceptionHandler({BusinessException.class})
  public ResponseEntity<String> handleBusinessException(BusinessException e) {
    return error(HttpStatus.valueOf(432), e); // Retorna um particular codigo 400
  }

  @ExceptionHandler({APIException.class})
  public ResponseEntity<String> handleAPIExceptionException(APIException e) {
    return error(HttpStatus.NOT_FOUND, e);
  }

  @ExceptionHandler({Exception.class})
  public ResponseEntity<String> handleException(Exception e) {
    return error(HttpStatus.INTERNAL_SERVER_ERROR, e);
  }

  private ResponseEntity<String> error(HttpStatus status, Exception e) {
    logger.error("error: ", e);
    return ResponseEntity.status(status).body(e.getMessage());
  }
}
