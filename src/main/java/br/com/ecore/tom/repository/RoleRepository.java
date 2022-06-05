package br.com.ecore.tom.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ecore.tom.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
}
