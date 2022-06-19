package br.com.ecore.tom.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
import com.fasterxml.jackson.databind.ObjectMapper;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.domain.dto.RoleDTO;
import br.com.ecore.tom.service.MembershipService;
import br.com.ecore.tom.service.RoleService;

@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RoleService service;

  @MockBean
  private MembershipService membershipService;

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must save a role")
  void must_save_a_role() throws Exception {
    URI uri = new URI("/roles");
    Role role = new Role("Developer_Test", "Develop applications");

    when(service.create(role)).thenReturn(role);

    mockMvc
        .perform(MockMvcRequestBuilders.post(uri).content(asJsonString(role))
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(201));
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must return a role by external id")
  void must_return_a_role_by_external_id() throws Exception {
    URI uri = new URI("/roles/" + UUID.randomUUID());
    Role role = new Role("Developer_Test", "Develop applications");

    when(service.findByExternalId(any(UUID.class))).thenReturn(role);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must return al roles")
  void must_return_al_roles() throws Exception {
    URI uri = new URI("/roles/");
    Role role = new Role("Developer_Test", "Develop applications");

    List<RoleDTO> roles = new ArrayList<>();
    roles.add(new RoleDTO(role));

    when(service.findAll()).thenReturn(roles);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }

  @Test
  @WithMockUser(username = "springtest")
  @DisplayName("Must return a role by membership")
  void must_return_a_role_by_membership() throws Exception {
    URI uri = new URI("/roles/" + UUID.randomUUID() + "/" + UUID.randomUUID());

    Role role = new Role("Developer_Test", "Develop applications");
    Membership membership =
        new Membership(new Member(UUID.randomUUID()), new Team(UUID.randomUUID(), "Dev test"));
    membership.setUuid(UUID.randomUUID());
    membership.setRole(role);

    when(membershipService.findByMembership(any(UUID.class), any(UUID.class)))
        .thenReturn(membership);

    mockMvc.perform(MockMvcRequestBuilders.get(uri).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(200));
  }

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
