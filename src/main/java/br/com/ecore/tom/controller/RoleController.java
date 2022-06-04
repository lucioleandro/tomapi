package br.com.ecore.tom.controller;

import java.net.URI;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.exceptions.APIExceptionUtils;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

  @Autowired
  private RoleService service;

  @PostMapping
  public ResponseEntity<Role> cadastraRole(@Valid @RequestBody Role role,
      UriComponentsBuilder uriBuilder) {
    APIExceptionUtils.assertValidPost(role.getId());
    Role roleSalva = service.create(role);
    URI uri = uriBuilder.path("/role/{id}").buildAndExpand(roleSalva.getId()).toUri();
    return ResponseEntity.created(uri).body(roleSalva);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Role> buscaRolePorId(@PathVariable Long id) {
    Role role = service.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Role.class));
    return ResponseEntity.ok(role);
  }

  @DeleteMapping("/{id}")
  public void remove(@PathVariable Long id) {
    // service.remove(id);
  }

}
