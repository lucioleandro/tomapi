package br.com.ecore.tom.service;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.MemberRepository;

@Service
public class MemberService {

  @Autowired
  private MemberRepository repository;

  public Member create(Member member) {
    return repository.save(member);
  }

  public Optional<Member> findById(Long id) {
    return repository.findById(id);
  }

  public void remove(Long id) {
    Member member = getMemberByIdOrElseThrow(id);
    repository.deleteById(member.getId());
  }

  private Member getMemberByIdOrElseThrow(Long id) {
    return this.repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(id, Member.class));
  }

}
