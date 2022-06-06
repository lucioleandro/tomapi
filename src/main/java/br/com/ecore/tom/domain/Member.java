package br.com.ecore.tom.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

@Entity
public class Member implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotNull
  @Column(nullable = false)
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID uuid;

  @NotEmpty
  @Size(max = 50, min = 3)
  @Column(nullable = false)
  private String displayName;

  @ManyToOne
  private Role role;

  public Member(Integer id, UUID uuid, String displayName) {
    this.id = id;
    this.uuid = uuid;
    this.displayName = displayName;
  }

  public Member(Integer id, UUID uuid, String displayName, Role role) {
    this.id = id;
    this.uuid = uuid;
    this.displayName = displayName;
    this.role = role;
  }

  public Member(UUID uuid, String displayName) {
    this.uuid = uuid;
    this.displayName = displayName;
  }

  public Member() {}

  public Integer getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getDisplayName() {
    return displayName;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.uuid);
  }

  @Override
  public boolean equals(Object obj) {
    Member other = (Member) obj;
    return uuid.equals(other.uuid);
  }

  @Override
  public String toString() {
    return "Member [displayName: " + displayName + ", role: " + role.getName() + "]";
  }

}
