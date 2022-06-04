package br.com.ecore.tom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ecore.tom.domain.Member;

public interface MemberRepository extends JpaRepository<Member, String> {
}
