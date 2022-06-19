package br.com.ecore.tom.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.domain.dto.MembershipDTO;
import br.com.ecore.tom.service.MembershipService;

@SpringBootTest
@AutoConfigureMockMvc
class MembsershipControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private MembershipService service;

  private Membership membership;

  private Role role;

  @BeforeEach
  public void setup() {
    Member member = new Member(UUID.randomUUID());
    Team team = new Team(UUID.randomUUID(), "Dev test");
    this.membership = new Membership(member, team);
    membership.setUuid(UUID.randomUUID());

    UUID roleExternalId = UUID.randomUUID();
    this.role = new Role(1, roleExternalId, "Developer",
        "Performs the function of implementing and maintaining system functionalities");

    membership.setRole(role);
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must assing a role to a membership")
  void must_assign_a_role_to_a_membership() throws Exception {
    URI uri = new URI("/memberships/assign/" + UUID.randomUUID() + "/" + UUID.randomUUID() + "/"
        + UUID.randomUUID());

    when(service.assignRole(any(UUID.class), any(UUID.class), any(UUID.class)))
        .thenReturn(membership);

    mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must assing a role to a membership using default role")
  void must_assign_a_role_to_a_membership_using_default_role() throws Exception {
    URI uri = new URI("/memberships/assign/" + UUID.randomUUID() + "/" + UUID.randomUUID());

    when(service.assignRole(any(UUID.class), any(UUID.class), any())).thenReturn(membership);

    mockMvc.perform(MockMvcRequestBuilders.patch(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must get a memberships given a role")
  void must_get_a_memberships_given_a_role() throws Exception {
    List<MembershipDTO> ships = new ArrayList<>();
    ships.add(new MembershipDTO(membership));

    URI uri = new URI("/memberships/role/" + UUID.randomUUID());

    when(service.findByRoleExternalId(any(UUID.class))).thenReturn(ships);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }


}
