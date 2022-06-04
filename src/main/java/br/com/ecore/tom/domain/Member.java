package br.com.ecore.tom.domain;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Member {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull
  private UUID uuid;

  @NotNull
  private String displayName;

  public Member(long id, UUID uuid, String displayName) {
    this.id = id;
    this.uuid = uuid;
    this.displayName = displayName;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getDisplayName() {
    return displayName;
  }

}
