package br.com.ecore.tom.domain.dto;

import java.util.UUID;
import br.com.ecore.tom.domain.Member;

public class MemberDTO {

  private UUID id;
  private String displayName;
  private RoleDTO role;

  public MemberDTO(UUID id, String displayName, RoleDTO role) {
    this.id = id;
    this.displayName = displayName;
    this.role = role;
  }

  public MemberDTO(Member member) {
    this.id = member.getUuid();
    this.displayName = member.getDisplayName();
    this.role = new RoleDTO(member.getRole());
  }

  public UUID getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public RoleDTO getRole() {
    return role;
  }

}
