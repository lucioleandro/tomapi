package br.com.ecore.tom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ecore.tom.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Integer> {
}
