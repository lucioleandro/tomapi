package br.com.ecore.tom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.RoleRepository;

@ExtendWith(MockitoExtension.class)
class RoleServiceTest {

  @InjectMocks
  private RoleService service;

  @Mock
  private RoleRepository repository;

  @Mock
  private MembershipService membershipService;

  @Mock
  private Role role;

  @BeforeEach
  public void setup() {
    this.role = new Role("Dev Test", "Dev test");
    this.role.setUuid(UUID.randomUUID());
  }

  @Test
  @DisplayName("Must save valid object")
  void must_save_a_role() {
    when(service.create(role)).thenReturn(mock(Role.class));
    when(repository.save(role)).thenReturn(role);

    Role roleSaved = this.service.create(role);
    assertNotNull(roleSaved);
    verify(repository).save(role);
  }

  @Test
  @DisplayName("Must return a role by its external id")
  void must_find_a_role_by_external_id() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.of(role));
    Role roleFound = service.findByExternalId(UUID.randomUUID());

    assertTrue(roleFound.getUuid().equals(role.getUuid()));
  }

  @Test
  @DisplayName("Must return a exception when the register is not in database")
  void must_throw_exception_when_externalId_is_not_in_database() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> service.findByExternalId(UUID.randomUUID()));
  }

  @Test
  @DisplayName("Must find a role by its name")
  void must_find_a_role_by_name() {
    when(repository.findByName("Developer")).thenReturn(Optional.of(role));
    Role roleFound = service.findByName("Developer");

    assertTrue(roleFound.getUuid().equals(role.getUuid()));
    assertEquals(roleFound.getName(), role.getName());
  }

  @Test
  @DisplayName("Must return a exception when the register with given name is not in database")
  void must_throw_exception_when_register_with_name_is_not_in_database() {
    when(repository.findByName("Developer")).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> service.findByName("Developer"));
  }

}
