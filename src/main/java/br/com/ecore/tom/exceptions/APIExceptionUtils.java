package br.com.ecore.tom.exceptions;

import java.util.Objects;

public class APIExceptionUtils {

  private APIExceptionUtils() {}

  public static void assertValidPost(Long id) {
    if (!Objects.isNull(id)) {
      throw new APIException("O id deve ser nulo para criação de novas entidades", 400L);
    }
  }

  public static void assertValidPut(Long id) {
    if (Objects.isNull(id)) {
      throw new APIException("Não é possível atualizar entidades sem id", 400L);
    }
  }
}
