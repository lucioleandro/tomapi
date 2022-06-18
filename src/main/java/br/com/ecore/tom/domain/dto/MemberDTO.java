package br.com.ecore.tom.domain.dto;

import java.util.UUID;
import br.com.ecore.tom.domain.Member;

public class MemberDTO {

  private UUID id;
  private String displayName;

  public MemberDTO(UUID id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  public MemberDTO(Member member) {
    this.id = member.getUuid();
    this.displayName = member.getDisplayName();
  }

  public UUID getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }

}
