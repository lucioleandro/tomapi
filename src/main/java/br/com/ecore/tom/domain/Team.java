package br.com.ecore.tom.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Team implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  private UUID uuid;

  @NotNull
  private String name;

  public Team(Integer id, UUID uuid, String name) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getName() {
    return name;
  }

}
