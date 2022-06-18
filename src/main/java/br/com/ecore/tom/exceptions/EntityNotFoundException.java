package br.com.ecore.tom.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Class<?> classe;
  private final Object serchedId;

  public EntityNotFoundException(Class<?> classe) {
    super("An instance of the class " + classe.getName() + " was not found.");
    this.serchedId = null;
    this.classe = classe;
  }

  public EntityNotFoundException(String message, Class<?> classe) {
    super("An instance of the class " + classe.getName() + " was not found. " + message);
    this.serchedId = null;
    this.classe = classe;
  }

  public EntityNotFoundException(Integer serchedId, Class<?> classe) {
    super("An instance of the class " + classe.getName() + " with id: " + serchedId
        + " was not found.");
    this.serchedId = serchedId;
    this.classe = classe;
  }

  public EntityNotFoundException(UUID serchedId, Class<?> classe) {
    super("An instance of the class " + classe.getName() + " with id: " + serchedId
        + " was not found.");
    this.serchedId = serchedId;
    this.classe = classe;
  }

  public EntityNotFoundException(String message, Throwable cause, Integer serchedId,
      Class<?> classe) {
    super(message, cause);
    this.serchedId = serchedId;
    this.classe = classe;
  }

  public EntityNotFoundException(String message, Integer idserchedId, Class<?> classe) {
    super(message);
    this.serchedId = idserchedId;
    this.classe = classe;
  }

  public EntityNotFoundException(Throwable cause, Integer serchedId, Class<?> classe) {
    super(cause);
    this.serchedId = serchedId;
    this.classe = classe;
  }

  public Object getId() {
    return serchedId;
  }

  public Class<?> getClasse() {
    return this.classe;
  }
}
