package br.com.ecore.tom.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ecore.tom.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Integer> {

  Optional<Member> findByUuid(UUID memberExternalId);

}
