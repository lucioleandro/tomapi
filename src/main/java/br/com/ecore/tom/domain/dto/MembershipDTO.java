package br.com.ecore.tom.domain.dto;

import java.util.UUID;
import br.com.ecore.tom.domain.Membership;

public class MembershipDTO {

  private UUID id;
  private MemberDTO member;
  private TeamDTO team;

  public MembershipDTO(UUID id, MemberDTO memberDTO, TeamDTO teamDTO) {
    this.id = id;
    this.member = memberDTO;
    this.team = teamDTO;
  }

  public MembershipDTO(Membership memberShip) {
    this.id = memberShip.getUuid();
    this.member = new MemberDTO(memberShip.getMember());
    this.team = new TeamDTO(memberShip.getTeam());
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

}
