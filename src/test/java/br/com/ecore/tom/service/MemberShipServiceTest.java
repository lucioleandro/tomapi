package br.com.ecore.tom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.domain.dto.MembershipDTO;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.integration.TeamConsumerService;
import br.com.ecore.tom.repository.MembershipRepository;

@ExtendWith(MockitoExtension.class)
class MemberShipServiceTest {

  @InjectMocks
  private MembershipService service;

  @Mock
  private MembershipRepository repository;

  @Mock
  private Membership membership;

  @Mock
  private RoleService roleService;

  @Mock
  private TeamConsumerService teamConsumerService;

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
  @DisplayName("Must save valid object")
  void must_save_a_membership() {
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
  @DisplayName("Must try fetching a membership when it is not in tom's database")
  void must_try_fetching_a_membership_when_it_is_not_in_tom_database() {
    when(repository.findByTeamUuidAndMemberUuid(any(UUID.class), any(UUID.class)))
        .thenAnswer(new Answer<Object>() {
          private int count = 1;

          public Object answer(InvocationOnMock invocation) {
            if (count++ == 1) {
              return Optional.empty();
            }
            return Optional.of(membership);
          }
        });

    Membership membershipFound = service.findByMembership(UUID.randomUUID(), UUID.randomUUID());

    assertTrue(membershipFound.getUuid().equals(membership.getUuid()));
  }

  @Test
  @DisplayName("Must return a exception when the register is not in database")
  void must_throw_exception_when_externalId_is_not_in_database() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());
    assertThrows(EntityNotFoundException.class, () -> service.findByExternalId(UUID.randomUUID()));
  }

  @Test
  @DisplayName("Must assign a default role to a membership")
  void must_assign_a_default_role_to_a_team_member() {
    when(roleService.findByName(any(String.class))).thenReturn(this.role);
    when(repository.findByTeamUuidAndMemberUuid(any(UUID.class), any(UUID.class)))
        .thenReturn(Optional.of(membership));
    when(repository.save(membership)).thenReturn(membership);
    Membership assignedMembership =
        this.service.assignRole(UUID.randomUUID(), UUID.randomUUID(), null);

    verify(repository).save(assignedMembership);
  }

  @Test
  @DisplayName("Must assign a specific role to a membership")
  void must_assign_a_specific_role_to_a_team_member() {
    UUID roleExternalId = this.role.getUuid();

    when(roleService.findByExternalId(roleExternalId)).thenReturn(this.role);
    when(repository.findByTeamUuidAndMemberUuid(any(UUID.class), any(UUID.class)))
        .thenReturn(Optional.of(membership));
    when(repository.save(membership)).thenReturn(membership);
    Membership assignedMembership =
        this.service.assignRole(UUID.randomUUID(), UUID.randomUUID(), roleExternalId);

    assertEquals(this.role.getUuid(), assignedMembership.getRole().getUuid());
    verify(repository).save(assignedMembership);
  }

  @Test
  @DisplayName("Must return a exception when the register is not in database nether in the other application")
  void must_throw_exception_when_member_ship_is_not_in_database_neither_in_the_other_application() {
    when(repository.findByTeamUuidAndMemberUuid(any(UUID.class), any(UUID.class)))
        .thenAnswer(new Answer<Object>() {
          private int count = 1;

          public Object answer(InvocationOnMock invocation) {
            if (count++ == 1) {
              return Optional.empty();
            }
            return Optional.empty();
          }
        });

    assertThrows(EntityNotFoundException.class,
        () -> service.findByMembership(UUID.randomUUID(), UUID.randomUUID()));
  }

  @Test
  @DisplayName("Must return a page of memberships")
  void must_return_a_page_of_memberships() {
    PageImpl<Membership> membershipMock = this.pageMembershipMock();

    when(repository.findAll(any(PageRequest.class))).thenReturn(membershipMock);
    Page<MembershipDTO> shipsFound = service.findAll(PageRequest.of(0, 20));

    assertTrue(shipsFound.getSize() == 1);
    assertEquals(shipsFound.getContent().get(0).getId(), membership.getUuid());
  }

  @Test
  @DisplayName("Must return a List of membership by a role")
  void must_return_a_list_of_membership_by_a_role() {
    List<MembershipDTO> ships = new ArrayList<>();
    ships.add(new MembershipDTO(membership));

    when(repository.findByRoleUuid(any(UUID.class))).thenReturn(ships);
    List<MembershipDTO> shipsFound = service.findByRoleExternalId(UUID.randomUUID());

    assertTrue(shipsFound.size() > 0);
    assertEquals(ships.get(0).getId(), membership.getUuid());
  }

  private PageImpl<Membership> pageMembershipMock() {
    List<Membership> ships = new ArrayList<>();
    ships.add(this.membership);

    return new PageImpl<Membership>(ships, PageRequest.of(0, 1), 1);
  }

}
