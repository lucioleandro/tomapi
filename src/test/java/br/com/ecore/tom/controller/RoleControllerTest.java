package br.com.ecore.tom.controller;

import java.net.URI;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc()
class RoleControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  void must_save_a_role() throws Exception {
    URI uri = new URI("/tomapi/roles");
    String json = "{\"name\": \"\", \"description\": \"Developer\": \"Test\"}";

    mockMvc
        .perform(
            MockMvcRequestBuilders.post(uri).content(json).contentType(MediaType.APPLICATION_JSON))
        .andExpect(MockMvcResultMatchers.status().is(401));

  }

}
