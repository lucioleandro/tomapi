package br.com.ecore.tom.exceptions;

import java.util.Objects;
import br.com.ecore.tom.domain.IEntity;

public class APIExceptionUtils {

  private APIExceptionUtils() {}

  public static void assertValidPost(IEntity entity) {
    if (!Objects.isNull(entity.getId()) || !Objects.isNull(entity.getUuid())) {
      throw new APIException("The ID must be null to create a new resource", 400L);
    }
  }

  public static void assertValidPut(IEntity entity) {
    if (Objects.isNull(entity.getUuid())) {
      throw new APIException("It is not possibe create a new Resource without an ID", 400L);
    }
  }
}
