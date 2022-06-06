package br.com.ecore.tom.service;

import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.integration.MemberConsumerService;
import br.com.ecore.tom.repository.MemberRepository;

@Service
public class MemberService {

  @Autowired
  private MemberRepository repository;

  @Autowired
  private RoleService roleService;

  @Autowired
  private MemberConsumerService memberConsumerService;

  public Member create(Member member) {
    return repository.save(member);
  }

  public Member assignRole(UUID memberExternalId, UUID roleExternalId) {
    Member member = this.findByExternalId(memberExternalId);
    Role role = roleService.findByExternalId(roleExternalId);
    member.setRole(role);

    return repository.save(member);
  }

  public Member findById(Integer id) {
    return repository.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Member.class));
  }

  public Member findByExternalId(UUID externalId) throws EntityNotFoundException {
    Optional<Member> optionalMember = repository.findByUuid(externalId);
    if (optionalMember.isPresent()) {
      return optionalMember.get();
    } else {
      return memberConsumerService.fetchMemberById(externalId);
    }
  }

  public void remove(Integer id) {
    Member member = getMemberByIdOrElseThrow(id);
    repository.deleteById(member.getId());
  }

  private Member getMemberByIdOrElseThrow(Integer id) {
    return this.repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(id, Member.class));
  }

}
