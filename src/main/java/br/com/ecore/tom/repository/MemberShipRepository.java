package br.com.ecore.tom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ecore.tom.domain.MemberShip;

public interface MemberShipRepository extends JpaRepository<MemberShip, Integer> {
}
