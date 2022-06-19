package br.com.ecore.tom.domain.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.com.ecore.tom.domain.Role;

@JsonInclude(Include.NON_NULL)
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

  public Role transformToRole() {
    return new Role(this.id, this.name, this.description);
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
