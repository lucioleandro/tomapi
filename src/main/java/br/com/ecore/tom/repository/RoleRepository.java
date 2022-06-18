package br.com.ecore.tom.repository;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import br.com.ecore.tom.domain.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

  Optional<Role> findByUuid(UUID uuid);

  Optional<Role> findByName(String name);

}
