package br.com.ecore.tom.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.dto.MembershipDTO;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {

  Optional<Membership> findByUuid(UUID externalId);

  @Query("SELECT ms from Membership ms where ms.member.role.uuid = ?1")
  List<MembershipDTO> findByRoleExternalId(UUID externalId);

}
