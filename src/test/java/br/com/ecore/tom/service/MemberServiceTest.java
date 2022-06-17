package br.com.ecore.tom.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
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

  @BeforeEach
  public void setup() {
    this.member = new Member(UUID.randomUUID(), "Dev test");
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
  @DisplayName("Must return a member by its external id")
  void must_find_a_member_by_external_id() {
    when(repository.findByUuid(any(UUID.class))).thenReturn(Optional.of(member));
    Member memberFound = service.findByExternalId(UUID.randomUUID());

    assertTrue(memberFound.getUuid().equals(member.getUuid()));
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
