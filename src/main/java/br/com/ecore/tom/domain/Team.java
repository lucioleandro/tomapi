package br.com.ecore.tom.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "team")
public class Team implements Serializable, IEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID uuid;

  @Column(nullable = false)
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private Member teamLead;

  public Team() {}

  public Team(Integer id, UUID uuid, String name, Member teamLead) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.teamLead = teamLead;
  }

  public Team(UUID uuid, String name, Member teamLead) {
    this.uuid = uuid;
    this.name = name;
    this.teamLead = teamLead;
  }

  public Team(UUID uuid, String name) {
    this.uuid = uuid;
    this.name = name;
  }

  public static long getSerialversionuid() {
    return serialVersionUID;
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

  public Member getTeamLead() {
    return teamLead;
  }

  public void setTeamLead(Member teamLead) {
    this.teamLead = teamLead;
  }

  @Override
  public int hashCode() {
    return Objects.hash(uuid);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (!(obj instanceof Team))
      return false;

    Team other = (Team) obj;
    return uuid.equals(other.uuid);
  }

  @Override
  public String toString() {
    return "Team [name: " + name + "]";
  }

}
