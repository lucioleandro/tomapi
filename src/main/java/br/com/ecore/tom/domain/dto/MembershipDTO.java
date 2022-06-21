package br.com.ecore.tom.domain.dto;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import br.com.ecore.tom.domain.Membership;

@JsonInclude(Include.NON_NULL)
public class MembershipDTO {

  private UUID id;
  private MemberDTO member;
  private TeamDTO team;
  private RoleDTO role;

  public MembershipDTO(UUID id, MemberDTO memberDTO, TeamDTO teamDTO, RoleDTO roleDTO) {
    this.id = id;
    this.member = memberDTO;
    this.team = teamDTO;
    this.role = roleDTO;
  }

  public MembershipDTO(Membership memberShip) {
    this.id = memberShip.getUuid();
    this.member = new MemberDTO(memberShip.getMember());
    this.team = new TeamDTO(memberShip.getTeam());
    this.role = new RoleDTO(memberShip.getRole());
  }
  
  public static List<MembershipDTO> transformToList(List<Membership> ships) {
    return ships.stream().map(MembershipDTO::new).collect(Collectors.toList());
  }

  public UUID getId() {
    return id;
  }

  public MemberDTO getMember() {
    return member;
  }

  public TeamDTO getTeam() {
    return team;
  }

  public RoleDTO getRole() {
    return role;
  }
  
}
