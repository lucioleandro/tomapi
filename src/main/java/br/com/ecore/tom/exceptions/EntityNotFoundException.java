package br.com.ecore.tom.exceptions;

public class EntityNotFoundException extends RuntimeException {

  private static final long serialVersionUID = 1L;

  private final Class<?> classe;
  private final String idBuscado;

  public EntityNotFoundException(String idBuscado, Class<?> classe) {
    super("Não foi encontrada uma instância da classe " + classe.getName() + " com o id: "
        + idBuscado);
    this.idBuscado = idBuscado;
    this.classe = classe;
  }

  public EntityNotFoundException(String message, Throwable cause, String idBuscado,
      Class<?> classe) {
    super(message, cause);
    this.idBuscado = idBuscado;
    this.classe = classe;
  }

  public EntityNotFoundException(String message, String ididBuscado, Class<?> classe) {
    super(message);
    this.idBuscado = ididBuscado;
    this.classe = classe;
  }

  public EntityNotFoundException(Throwable cause, String idBuscado, Class<?> classe) {
    super(cause);
    this.idBuscado = idBuscado;
    this.classe = classe;
  }

  public String getId() {
    return idBuscado;
  }

  public Class<?> getClasse() {
    return this.classe;
  }
}
