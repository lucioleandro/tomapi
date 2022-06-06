package br.com.ecore.tom.domain.dto;

import java.util.UUID;
import br.com.ecore.tom.domain.MemberShip;

public class MemberShipDTO {

  private UUID id;
  private MemberDTO memberDTO;
  private TeamDTO teamDTO;

  public MemberShipDTO(UUID id, MemberDTO memberDTO, TeamDTO teamDTO) {
    this.id = id;
    this.memberDTO = memberDTO;
    this.teamDTO = teamDTO;
  }

  public MemberShipDTO(MemberShip memberShip) {
    this.id = memberShip.getUuid();
    this.memberDTO = new MemberDTO(memberShip.getMember());
    this.teamDTO = new TeamDTO(memberShip.getTeam());
  }

  public UUID getId() {
    return id;
  }

  public MemberDTO getMemberDTO() {
    return memberDTO;
  }

  public TeamDTO getTeamDTO() {
    return teamDTO;
  }

}
