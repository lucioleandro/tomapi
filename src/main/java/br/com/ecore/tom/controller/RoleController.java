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
import br.com.ecore.tom.service.MembershipService;
import br.com.ecore.tom.service.RoleService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/roles")
public class RoleController {

  @Autowired
  private RoleService service;

  @Autowired
  private MembershipService membershipService;

  @PostMapping
  @ApiOperation(value = "Create a role",
      notes = "Creates a role and returns the created role with its respective resource")
  public ResponseEntity<RoleDTO> createRole(@Valid @RequestBody RoleDTO roleDTO,
      UriComponentsBuilder uriBuilder) {
    Role role = roleDTO.transformToRole();
    APIExceptionUtils.assertValidPost(role);
    Role savedRole = service.create(role);
    URI uri = uriBuilder.path("/role/{id}").buildAndExpand(savedRole.getUuid()).toUri();
    return ResponseEntity.created(uri).body(new RoleDTO(savedRole));
  }

  @GetMapping("/{externalId}")
  @ApiOperation(value = "Find a role by its external id",
      notes = "Finds a role by the external Id and return its DTO")
  public ResponseEntity<RoleDTO> lookupByExternalId(@PathVariable UUID externalId) {
    Role role = service.findByExternalId(externalId);
    return ResponseEntity.ok(new RoleDTO(role));
  }

  @GetMapping
  @ApiOperation(value = "Find all roles", notes = "Get all roles and returns a list of its DTO's")
  public ResponseEntity<List<RoleDTO>> lookupAll() {
    return ResponseEntity.ok(service.findAll());
  }

  @GetMapping("/{teamExternalId}/{memberExternalId}")
  @ApiOperation(value = "Find a role by membership",
      notes = "Given a membership definet by a team and a member, return its associated role")
  public ResponseEntity<RoleDTO> lookupRoleByMembership(@PathVariable UUID teamExternalId,
      @PathVariable UUID memberExternalId) {
    Role role = membershipService.findByMembership(teamExternalId, memberExternalId).getRole();
    return ResponseEntity.ok(new RoleDTO(role));
  }

}
