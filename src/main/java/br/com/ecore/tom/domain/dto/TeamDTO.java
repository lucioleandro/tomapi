package br.com.ecore.tom.domain.dto;

import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.com.ecore.tom.domain.Team;

@JsonInclude(Include.NON_NULL)
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
