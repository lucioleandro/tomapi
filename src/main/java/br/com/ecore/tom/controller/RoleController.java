package br.com.ecore.tom.controller;

import java.net.URI;
import java.util.List;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.domain.dto.RoleDTO;
import br.com.ecore.tom.exceptions.APIExceptionUtils;
import br.com.ecore.tom.service.RoleService;

@RestController
@RequestMapping("/role")
public class RoleController {

  @Autowired
  private RoleService service;

  @PostMapping
  public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO,
      UriComponentsBuilder uriBuilder) {
    Role role = roleDTO.transformToRole();
    APIExceptionUtils.assertValidPost(role.getId());
    Role savedRole = service.create(role);
    URI uri = uriBuilder.path("/role/{id}").buildAndExpand(savedRole.getUuid()).toUri();
    return ResponseEntity.created(uri).body(new RoleDTO(savedRole));
  }

  @GetMapping
  public ResponseEntity<List<RoleDTO>> listAllRoles() {
    List<Role> roles = service.findAll();
    return ResponseEntity.ok(RoleDTO.convertList(roles));
  }

  @GetMapping("/{externalId}")
  public ResponseEntity<RoleDTO> lookUpRoleById(@PathVariable UUID externalId) {
    Role role = service.findByExternalId(externalId);
    return ResponseEntity.ok(new RoleDTO(role));
  }

  @GetMapping("/membership/{membershipExternalId}")
  public ResponseEntity<RoleDTO> lookUpRoleByMembership(@PathVariable UUID membershipExternalId) {
    Role role = service.findByMembershipExternalId(membershipExternalId);
    return ResponseEntity.ok(new RoleDTO(role));
  }

}
