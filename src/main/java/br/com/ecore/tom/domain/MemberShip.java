package br.com.ecore.tom.domain;

import java.io.Serializable;
import java.util.UUID;
import javax.persistence.Column;
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

  @Column(nullable = false)
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

  public Integer getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public Member getMember() {
    return member;
  }

  public Team getTeam() {
    return team;
  }

  @Override
  public String toString() {
    return "MemberShip [member: " + member.getDisplayName() + ", team: " + team.getName() + "]";
  }

}
