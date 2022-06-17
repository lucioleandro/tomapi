package br.com.ecore.tom.service;

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
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.MembershipRepository;

@ExtendWith(MockitoExtension.class)
class MemberShipServiceTest {

  @InjectMocks
  private MembershipService service;

  @Mock
  private MembershipRepository repository;

  @Mock
  private Membership membership;

  @BeforeEach
  public void setup() {
    Member member = new Member(UUID.randomUUID());
    Team team = new Team(UUID.randomUUID(), "Dev test");
    this.membership = new Membership(member, team);
    membership.setUuid(UUID.randomUUID());
  }

  @Test
  @DisplayName("Must save valid object")
  void must_save_a_role() {
    when(service.create(membership)).thenReturn(mock(Membership.class));
    when(repository.save(membership)).thenReturn(membership);

    Membership membershipSaved = this.service.create(membership);
    assertNotNull(membershipSaved);
    verify(repository).save(membershipSaved);
  }

  @Test
  @DisplayName("Must return a membership by its external id")
  void must_find_a_membership_by_external_id() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.of(membership));
    Membership membershipFound = service.findByExternalId(UUID.randomUUID());

    assertTrue(membershipFound.getUuid().equals(membership.getUuid()));
  }

  @Test
  @DisplayName("Must return a exception when the register is not in database")
  void must_throw_exception_when_externalId_is_not_in_database() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> service.findByExternalId(UUID.randomUUID()));
  }



}
