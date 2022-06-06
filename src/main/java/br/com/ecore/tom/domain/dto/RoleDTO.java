package br.com.ecore.tom.domain.dto;

import java.util.UUID;
import br.com.ecore.tom.domain.Role;

public class RoleDTO {

  private UUID id;
  private String name;
  private String description;

  public RoleDTO(UUID id, String name, String description) {
    this.id = id;
    this.name = name;
    this.description = description;
  }

  public RoleDTO(Role role) {
    this.id = role.getUuid();
    this.name = role.getName();
    this.description = role.getDescription();
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

}
