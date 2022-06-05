package br.com.ecore.tom.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MemberShip implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  private Member member;

  @ManyToOne
  private Team team;

  public MemberShip(Long id, Member member, Team team) {
    this.id = id;
    this.member = member;
    this.team = team;
  }

  public Member getMember() {
    return member;
  }

  public Team getTeam() {
    return team;
  }

}
