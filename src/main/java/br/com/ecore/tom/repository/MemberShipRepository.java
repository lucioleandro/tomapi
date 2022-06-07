package br.com.ecore.tom.repository;

import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.ecore.tom.domain.MemberShip;
import br.com.ecore.tom.domain.dto.MemberShipDTO;

public interface MemberShipRepository extends JpaRepository<MemberShip, Integer> {

  @Query("SELECT ms from MemberShip ms where ms.member.role.uuid = ?1")
  List<MemberShipDTO> findByRoleExternalId(UUID externalId);

}
