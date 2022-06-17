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
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.TeamRepository;

@ExtendWith(MockitoExtension.class)
class TeamServiceTest {

  @InjectMocks
  private TeamService service;

  @Mock
  private TeamRepository repository;

  @Mock
  private MembershipService membershipService;

  @Mock
  private Team team;

  @BeforeEach
  public void setup() {
    this.team = new Team(UUID.randomUUID(), "Dev test");
    Member member = new Member();
    this.team.setTeamLead(member);
  }

  @Test
  @DisplayName("Must save valid object")
  void must_save_a_team() {
    when(service.create(team)).thenReturn(mock(Team.class));
    when(repository.save(team)).thenReturn(team);

    Team teamSaved = this.service.create(team);
    assertNotNull(teamSaved);
    verify(repository).save(team);
  }

  @Test
  @DisplayName("Must return a team by its external id")
  void must_find_a_team_by_external_id() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.of(team));
    Team teamFound = service.findByExternalId(UUID.randomUUID());

    assertTrue(teamFound.getUuid().equals(team.getUuid()));
  }

  @Test
  @DisplayName("Must return a exception when the register is not in database neither")
  void must_throw_exception_when_externalId_is_not_in_database() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> service.findByExternalId(UUID.randomUUID()));
  }

}
