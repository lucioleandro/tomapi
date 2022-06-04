package br.com.ecore.tom.authentication.domain;

import javax.validation.constraints.NotBlank;

public class LoginDTO {

  @NotBlank
  private String userName;

  @NotBlank
  private String password;

  public String getUserName() {
    return userName;
  }

  public String getPassword() {
    return password;
  }

}
