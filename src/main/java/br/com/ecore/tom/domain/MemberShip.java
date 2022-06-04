package br.com.ecore.tom.domain;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
public class MemberShip implements Serializable {

  private static final long serialVersionUID = 1L;

  @ManyToMany
  private Member member;

  @ManyToMany
  private Team team;

  public MemberShip(Member member, Team team) {
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
