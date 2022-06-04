package br.com.ecore.tom.domain;

import java.util.UUID;
import javax.persistence.Entity;

@Entity
public class Team {

  private Long id;
  private UUID uuid;

  public Team(long id, UUID uuid) {
    this.id = id;
    this.uuid = uuid;
  }

  public Long getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

}
