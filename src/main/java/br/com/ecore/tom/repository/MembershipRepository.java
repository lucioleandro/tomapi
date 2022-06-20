package br.com.ecore.tom.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.dto.MembershipDTO;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {

  Optional<Membership> findByUuid(UUID externalId);

  List<MembershipDTO> findByRoleUuid(UUID externalId);

  Optional<Membership> findByTeamUuidAndMemberUuid(UUID teamExternalID, UUID memberExternalId);

  List<Membership> findByTeamUuid(UUID teamExternalId);

}
