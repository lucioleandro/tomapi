package br.com.ecore.tom.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.repository.RoleRepository;

@Service
public class RoleService {

  @Autowired
  private RoleRepository repository;

  public Role create(Role role) {
    return repository.save(role);
  }

  public List<Role> findAll() {
    return repository.findAll();
  }

  public Optional<Role> findById(Long id) {
    return repository.findById(id);
  }
}