package br.com.ecore.tom.service;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import org.aspectj.lang.annotation.Before;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.repository.RoleRepository;

public class RoleServiceTest {

  @InjectMocks
  private RoleService service;

  @Mock
  private RoleRepository repository;

  @Rule
  public ExpectedException thrown = ExpectedException.none();

  private Role role;


  @Before
  public void setup() {
    role = new Role("Developer", "Developer Description");
  }

  @Test
  public void must_save_a_role() {
    Role retorno = this.service.create(role);
    assertNotNull(retorno);
    verify(repository).save(role);
  }

}
