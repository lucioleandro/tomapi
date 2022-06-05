package br.com.ecore.tom.integration;

import java.util.UUID;
import br.com.ecore.tom.domain.Member;

public class UserConsumerDTO {

  private UUID id;
  private String displayName;

  public UserConsumerDTO(UUID id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  public UUID getId() {
    return id;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Member transformToMember() {
    return new Member(this.id, this.displayName); // this.id is set to Member.uuid
  }

}
