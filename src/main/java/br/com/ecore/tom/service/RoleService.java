package br.com.ecore.tom.service;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

  public Role findByExternalId(UUID externalId) {
    return repository.findByUuid(externalId)
        .orElseThrow(() -> new EntityNotFoundException(externalId, Role.class));
  }

  public Role findByName(String name) {
    return repository.findByName(name).orElseThrow(
        () -> new EntityNotFoundException("Thre is no Role with name: " + name, Role.class));
  }

}
