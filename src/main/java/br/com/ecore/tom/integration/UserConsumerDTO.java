package br.com.ecore.tom.integration;

import java.util.UUID;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.Role;

public class UserConsumerDTO {

  private UUID id;
  private String firstName;
  private String lastName;
  private String displayName;
  private String avatarUrl;
  private String location;

  public UserConsumerDTO(UUID id, String firstName, String lastName, String displayName,
      String avatarUrl, String location) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.displayName = displayName;
    this.avatarUrl = avatarUrl;
    this.location = location;
  }

  public UserConsumerDTO() {}

  public UserConsumerDTO(UUID id, String displayName) {
    this.id = id;
    this.displayName = displayName;
  }

  public UUID getId() {
    return id;
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

  public Member transformToMember() {
    return new Member(this.id, this.firstName, this.lastName, this.displayName, this.avatarUrl,
        this.location, new Role(1)); // this.id is set to Member.uuid
  }

}
