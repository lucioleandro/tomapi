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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
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
import br.com.ecore.tom.domain.dto.TeamDTO;
import br.com.ecore.tom.service.MembershipService;

@SpringBootTest
@AutoConfigureMockMvc
class MembershipControllerTest {

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
  @DisplayName("Must get a membership given an external ID")
  void must_get_a_membership_given_an_external_id() throws Exception {
    URI uri = new URI("/memberships/" + UUID.randomUUID());

    when(service.findByExternalId(any(UUID.class))).thenReturn(membership);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must get a membership given an team_id and member ID")
  void must_get_a_membership_given_an_Team_id_and_member_id() throws Exception {
    URI uri = new URI(
        "/memberships/" + membership.getTeam().getUuid() + "/" + membership.getMember().getUuid());

    when(service.findByMembership(membership.getTeam().getUuid(), membership.getMember().getUuid()))
        .thenReturn(membership);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must get page of memberships")
  void must_get_a_page_of_memberships() throws Exception {
    Page<MembershipDTO> membershipMock = this.pageMembershipMock();

    List<MembershipDTO> ships = new ArrayList<>();
    ships.add(new MembershipDTO(membership));

    URI uri = new URI("/memberships/");

    when(service.findAll(any(PageRequest.class))).thenReturn(membershipMock);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$.size").isNotEmpty());
  }
  
  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must get all memberships from the other application")
  void must_get_all_memberships_from_the_other_application() throws Exception {
    List<TeamDTO> teams = new ArrayList<>();
    teams.add(new TeamDTO(UUID.randomUUID(), "Test team"));

    URI uri = new URI("/memberships/fetch");

    when(service.fetchAllFromOtherApplication()).thenReturn(teams);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must get memberships given a role")
  void must_get_a_memberships_given_a_role() throws Exception {
    List<MembershipDTO> ships = new ArrayList<>();
    ships.add(new MembershipDTO(membership));

    URI uri = new URI("/memberships/role/" + UUID.randomUUID());

    when(service.findByRoleExternalId(any(UUID.class))).thenReturn(ships);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200))
        .andExpect(MockMvcResultMatchers.jsonPath("$[*].id").isNotEmpty());
  }

  private PageImpl<MembershipDTO> pageMembershipMock() {
    List<MembershipDTO> ships = new ArrayList<>();
    ships.add(new MembershipDTO(this.membership));

    return new PageImpl<MembershipDTO>(ships, PageRequest.of(0, 1), 1);
  }


}
