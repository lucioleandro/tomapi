package br.com.ecore.tom.controller;

import static org.mockito.Mockito.when;
import java.net.URI;
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
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.service.RoleService;

@SpringBootTest
@AutoConfigureMockMvc
class RoleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private RoleService service;

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

  private static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

}
