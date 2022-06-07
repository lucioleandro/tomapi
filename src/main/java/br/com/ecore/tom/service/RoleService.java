package br.com.ecore.tom.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.RoleRepository;

@Service
public class RoleService {

  @Autowired
  private RoleRepository repository;

  @Transactional
  public Role create(Role role) {
    role.setUuid(UUID.randomUUID());
    return repository.save(role);
  }

  public List<Role> findAll() {
    return repository.findAll();
  }

  public Optional<Role> findById(Integer id) {
    return repository.findById(id);
  }

  public Role findByExternalId(UUID externalId) {
    return repository.findByUuid(externalId)
        .orElseThrow(() -> new EntityNotFoundException(externalId, Role.class));

  }
}
