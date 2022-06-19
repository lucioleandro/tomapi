package br.com.ecore.tom.integration;

import java.util.List;
import java.util.UUID;
import br.com.ecore.tom.domain.Team;

public class TeamConsumerDTO {

  private UUID id;
  private String name;
  private UUID teamLeadId;
  private List<UUID> teamMemberIds;

  public TeamConsumerDTO(UUID id, String name, UUID teamLeadId, List<UUID> teamMemberIds) {
    this.id = id;
    this.name = name;
    this.teamLeadId = teamLeadId;
    this.teamMemberIds = teamMemberIds;
  }

  public TeamConsumerDTO(UUID id, String name) {
    this.id = id;
    this.name = name;
  }

  public TeamConsumerDTO() {}

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public UUID getTeamLeadId() {
    return teamLeadId;
  }

  public List<UUID> getTeamMemberIds() {
    return teamMemberIds;
  }

  public Team transformToTeam() {
    return new Team(this.id, this.name);
  }

}
