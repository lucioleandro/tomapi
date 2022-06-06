package br.com.ecore.tom.exceptions;

import java.util.UUID;

public class EntityNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Class<?> classe;
  private final Object idBuscado;

  public EntityNotFoundException(Integer idBuscado, Class<?> classe) {
    super("Não foi encontrada uma instância da classe " + classe.getName() + " com o id: "
        + idBuscado);
    this.idBuscado = idBuscado;
    this.classe = classe;
  }

  public EntityNotFoundException(UUID idBuscado, Class<?> classe) {
    super("Não foi encontrada uma instância da classe " + classe.getName() + " com o id: "
        + idBuscado);
    this.idBuscado = idBuscado;
    this.classe = classe;
  }

  public EntityNotFoundException(String message, Throwable cause, Integer idBuscado,
      Class<?> classe) {
    super(message, cause);
    this.idBuscado = idBuscado;
    this.classe = classe;
  }

  public EntityNotFoundException(String message, Integer ididBuscado, Class<?> classe) {
    super(message);
    this.idBuscado = ididBuscado;
    this.classe = classe;
  }

  public EntityNotFoundException(Throwable cause, Integer idBuscado, Class<?> classe) {
    super(cause);
    this.idBuscado = idBuscado;
    this.classe = classe;
  }

  public Object getId() {
    return idBuscado;
  }

  public Class<?> getClasse() {
    return this.classe;
  }
}
