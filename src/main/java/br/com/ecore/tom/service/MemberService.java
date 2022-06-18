package br.com.ecore.tom.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.integration.MemberConsumerService;
import br.com.ecore.tom.repository.MemberRepository;

@Service
public class MemberService {

  @Autowired
  private MemberRepository repository;

  @Autowired
  private MemberConsumerService memberConsumerService;

  @Transactional
  public Member create(Member member) {
    return repository.save(member);
  }

  @Transactional
  public List<Member> createAll(List<Member> membersToSave) {
    return repository.saveAll(membersToSave);
  }

  @Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
  public Member findByExternalId(UUID externalId) throws EntityNotFoundException {
    Optional<Member> optionalMember = repository.findByUuid(externalId);
    if (optionalMember.isPresent()) {
      return optionalMember.get();
    } else {
      return memberConsumerService.fetchMemberById(externalId);
    }
  }

  public List<Member> findAll() {
    return repository.findAll();
  }

}
