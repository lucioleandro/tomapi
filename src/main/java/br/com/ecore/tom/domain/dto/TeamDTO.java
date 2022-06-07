package br.com.ecore.tom.domain.dto;

import java.util.UUID;
import br.com.ecore.tom.domain.Team;

public class TeamDTO {

  private UUID id;
  private String name;

  public TeamDTO(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  public TeamDTO(Team team) {
    this.id = team.getUuid();
    this.name = team.getName();
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

}
