package br.com.ecore.tom.domain;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.Type;

// TODO: Implementar builder
@Entity
@Table(name = "member")
public class Member implements Serializable, IEntity {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID uuid;

  @Column(nullable = true, length = 30)
  private String firstName;

  @Column(nullable = true, length = 30)
  private String lastName;

  @Column(nullable = false)
  private String displayName;

  private String avatarUrl;

  private String location;

  public Member() {}

  public Member(UUID uuid) {
    this.uuid = uuid;
  }

  public Member(Integer id, UUID uuid, String firstName, String lastName, String displayName,
      String avatarUrl, String location) {
    this.id = id;
    this.uuid = uuid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.displayName = displayName;
    this.avatarUrl = avatarUrl;
    this.location = location;
  }

  public Member(UUID uuid, String firstName, String lastName, String displayName, String avatarUrl,
      String location) {
    this.uuid = uuid;
    this.firstName = firstName;
    this.lastName = lastName;
    this.displayName = displayName;
    this.avatarUrl = avatarUrl;
    this.location = location;
  }

  public Member(UUID uuid, String displayName) {
    this.uuid = uuid;
    this.displayName = displayName;
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
    return "Member [displayName: " + displayName + "]";
  }

}
