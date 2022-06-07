package br.com.ecore.tom.domain.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import br.com.ecore.tom.domain.Role;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

public class RoleDTO {

  private UUID id;

  @NotEmpty
  @Size(min = 2, max = 50)
  private String name;

  @NotEmpty
  @Size(min = 10, max = 255)
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

  public static List<RoleDTO> convertList(List<Role> roles) {
    return roles.stream().map(RoleDTO::new).collect(Collectors.toList());
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
