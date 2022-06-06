package br.com.ecore.tom.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import org.hibernate.annotations.Type;

@Entity
public class Team implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID uuid;

  @NotEmpty
  @Column(nullable = false)
  private String name;

  @ManyToOne
  private Role role;

  public Team(Integer id, UUID uuid, String name) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
  }

  public Team(Integer id, UUID uuid, String name, Role role) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.role = role;
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

  public Role getRole() {
    return role;
  }

  @Override
  public String toString() {
    return "Team [name: " + name + "]";
  }

}
