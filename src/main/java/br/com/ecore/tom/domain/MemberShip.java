package br.com.ecore.tom.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.Type;

@Entity
public class MemberShip implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID uuid;

  @ManyToOne
  private Member member;

  @ManyToOne
  private Team team;

  public MemberShip(Integer id, UUID uuid, Member member, Team team) {
    this.id = id;
    this.uuid = uuid;
    this.member = member;
    this.team = team;
  }

  public MemberShip(Member member, Team team) {
    this.member = member;
    this.team = team;
  }

  public Integer getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public void setUuid(UUID uuid) {
    this.uuid = uuid;
  }

  public Member getMember() {
    return member;
  }

  public Team getTeam() {
    return team;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, uuid);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;

    if (obj == null)
      return false;

    if (getClass() != obj.getClass())
      return false;

    MemberShip other = (MemberShip) obj;
    return Objects.equals(id, other.id) && Objects.equals(uuid, other.uuid);
  }

  @Override
  public String toString() {
    return "MemberShip [member: " + member.getDisplayName() + ", team: " + team.getName() + "]";
  }

}
