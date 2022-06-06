package br.com.ecore.tom.domain.dto;

import java.util.UUID;
import br.com.ecore.tom.domain.Team;

public class TeamDTO {

  private UUID id;
  private String name;
  private RoleDTO role;

  public TeamDTO(UUID id, String name, RoleDTO role) {
    this.id = id;
    this.name = name;
    this.role = role;
  }

  public TeamDTO(Team team) {
    this.id = team.getUuid();
    this.name = team.getName();
    this.role = new RoleDTO(team.getRole());
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public RoleDTO getRole() {
    return role;
  }

}
