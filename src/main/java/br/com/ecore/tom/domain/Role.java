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
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import org.hibernate.annotations.Type;

@Entity
@Table(name = "role")
public class Role implements Serializable {

  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  @Type(type = "org.hibernate.type.UUIDCharType")
  private UUID uuid;

  @NotEmpty
  @Size(max = 50, min = 2)
  @Column(nullable = false)
  private String name;

  @NotEmpty
  @Size(max = 255)
  @Column(nullable = false)
  private String description;

  public Role() {}

  public Role(Integer id, UUID uuid, String name, String description) {
    this.id = id;
    this.uuid = uuid;
    this.name = name;
    this.description = description;
  }

  public Role(UUID uuid) {
    this.uuid = uuid;
  }

  public Role(Integer id) {
    this.id = id;
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

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
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

    Role other = (Role) obj;
    return Objects.equals(id, other.id) && Objects.equals(uuid, other.uuid);
  }

  @Override
  public String toString() {
    return "Role [name: " + name + ", description: " + description + "]";
  }

}
