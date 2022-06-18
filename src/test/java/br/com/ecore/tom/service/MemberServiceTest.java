package br.com.ecore.tom.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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
import org.mockito.junit.jupiter.MockitoExtension;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.integration.MemberConsumerService;
import br.com.ecore.tom.repository.MemberRepository;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

  @InjectMocks
  private MemberService service;

  @Mock
  private MemberRepository repository;

  @Mock
  private MemberConsumerService memberConsumerService;

  @Mock
  private Member member;

  private List<Member> members;

  @BeforeEach
  public void setup() {
    this.member = new Member(UUID.randomUUID(), "Dev test");
    members = new ArrayList<>();
    members.add(member);
  }

  @Test
  @DisplayName("Must save valid object")
  void must_save_a_member() {
    when(service.create(member)).thenReturn(mock(Member.class));
    when(repository.save(member)).thenReturn(member);

    Member memberSaved = this.service.create(member);
    assertNotNull(memberSaved);
    verify(repository).save(member);
  }

  @Test
  @DisplayName("Must save a list of valid objects")
  void must_save_a_list_of_member() {
    when(repository.saveAll(members)).thenReturn(members);

    List<Member> savedMembers = this.service.createAll(members);
    assertNotNull(savedMembers);
    verify(repository).saveAll(members);
  }

  @Test
  @DisplayName("Must return a member by its external id")
  void must_find_a_member_by_external_id() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.of(member));
    Member memberFound = service.findByExternalId(UUID.randomUUID());

    assertTrue(memberFound.getUuid().equals(member.getUuid()));
  }

  @Test
  @DisplayName("Must fetch a member by its external id")
  void must_fetch_a_member_by_external_id() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.empty());
    when(memberConsumerService.fetchMemberById(any(UUID.class))).thenReturn(member);
    Member memberFound = service.findByExternalId(UUID.randomUUID());

    assertTrue(memberFound.getUuid().equals(member.getUuid()));
  }

  @Test
  @DisplayName("Must return a list with all members")
  void must_find_all_members() {
    when(repository.findAll()).thenReturn(members);
    List<Member> membersFound = service.findAll();

    assertTrue(membersFound.size() > 0);
    assertEquals(membersFound.get(0).getUuid(), member.getUuid());
  }

  @Test
  @DisplayName("Must return a exception when the register is not in database")
  void must_throw_exception_when_externalId_is_not_in_database() {
    UUID uuid = UUID.randomUUID();
    when(repository.findByUuid(uuid)).thenReturn(Optional.empty());
    doThrow(new EntityNotFoundException(uuid, Member.class)).when(memberConsumerService)
        .fetchMemberById(uuid);
    assertThrows(EntityNotFoundException.class, () -> service.findByExternalId(uuid));
  }

}
