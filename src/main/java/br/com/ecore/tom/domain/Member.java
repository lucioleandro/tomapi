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

  @Column(nullable = true, length = 30)
  private String firstName;

  @Column(nullable = true, length = 30)
  private String lastName;

  @NotEmpty
  @Size(max = 30, min = 3)
  @Column(nullable = false)
  private String displayName;

  private String avatarUrl;

  private String location;

  @ManyToOne
  private Role role;

  public Member() {}

  public Member(Integer id, UUID uuid, String firstName, String lastName, String displayName,
      String avatarUrl, String location, Role role) {
    this.id = id;
    this.uuid = uuid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.displayName = displayName;
    this.avatarUrl = avatarUrl;
    this.location = location;
    this.role = role;
  }

  public Member(UUID uuid, String firstName, String lastName, String displayName, String avatarUrl,
      String location, Role role) {
    this.uuid = uuid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.displayName = displayName;
    this.avatarUrl = avatarUrl;
    this.location = location;
    this.role = role;
  }

  public Member(UUID uuid, String displayName) {
    this.uuid = uuid;
    this.displayName = displayName;
  }

  public Role getRole() {
    return role;
  }

  public void setRole(Role role) {
    this.role = role;
  }

  public Integer getId() {
    return id;
  }

  public UUID getUuid() {
    return uuid;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getDisplayName() {
    return displayName;
  }

  public String getAvatarUrl() {
    return avatarUrl;
  }

  public String getLocation() {
    return location;
  }

  @Override
  public int hashCode() {
    return Objects.hash(this.uuid);
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == null)
      return false;

    if (this.getClass() != obj.getClass())
      return false;

    Member other = (Member) obj;
    return uuid.equals(other.uuid);
  }

  @Override
  public String toString() {
    return "Member [displayName: " + displayName + ", role: " + role.getName() + "]";
  }

}
