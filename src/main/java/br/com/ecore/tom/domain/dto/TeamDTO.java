package br.com.ecore.tom.domain.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.com.ecore.tom.domain.Team;

@JsonInclude(Include.NON_NULL)
public class TeamDTO {

  private UUID id;
  private String name;
  private MemberDTO lead;
  private List<MemberDTO> members;

  public TeamDTO(UUID id, String name) {
    this.id = id;
    this.name = name;
    this.members = new ArrayList<>();
  }

  public TeamDTO(Team team) {
    this.id = team.getUuid();
    this.name = team.getName();
    this.members = new ArrayList<>();
  }

  public UUID getId() {
    return id;
  }

  public String getName() {
    return name;
  }
  
  public MemberDTO getLead() {
    return lead;
  }
  
  public void setLead(MemberDTO lead) {
    this.lead = lead;
  }
  
  public List<MemberDTO> getMembers() {
    return members;
  }
  
  public void addMember(MemberDTO dto) {
    this.members.add(dto);
  }

}
