package br.com.ecore.tom.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.domain.dto.MembershipDTO;
import br.com.ecore.tom.domain.dto.RoleDTO;
import br.com.ecore.tom.service.MembershipService;

@RestController
@RequestMapping("/memberships")
public class MembershipController {

  @Autowired
  private MembershipService service;

  @PatchMapping("assign/{teamExternalId}/{memberExternalId}/{roleExternalId}")
  public ResponseEntity<MembershipDTO> assignRoleToMember(@PathVariable UUID teamExternalId,
      @PathVariable UUID memberExternalId, @PathVariable(required = false) UUID roleExternalId) {
    Membership updatedMembership =
        service.assignRole(teamExternalId, memberExternalId, roleExternalId);
    return ResponseEntity.ok(new MembershipDTO(updatedMembership));
  }

  @PatchMapping("assign/{teamExternalId}/{memberExternalId}")
  public ResponseEntity<MembershipDTO> assignRoleToMember(@PathVariable UUID teamExternalId,
      @PathVariable UUID memberExternalId) {
    Membership updatedMembership = service.assignRole(teamExternalId, memberExternalId, null);
    return ResponseEntity.ok(new MembershipDTO(updatedMembership));
  }

  @GetMapping("/{id}")
  public ResponseEntity<Membership> lookupByExternalId(@PathVariable UUID externalId) {
    Membership memberShip = service.findByExternalId(externalId);
    return ResponseEntity.ok(memberShip);
  }

  @GetMapping("role/{roleExternalId}")
  public ResponseEntity<List<MembershipDTO>> lookupByRole(@PathVariable UUID roleExternalId) {
    List<MembershipDTO> memberShips = service.findByRoleExternalId(roleExternalId);
    return ResponseEntity.ok(memberShips);
  }

  @GetMapping("/role/{teamExternalId}/{membershipExternalId}")
  public ResponseEntity<RoleDTO> lookupRoleByMembership(@PathVariable UUID teamExternalId,
      @PathVariable UUID memberExternalId) {
    Role role = service.findByMembership(teamExternalId, memberExternalId).getRole();
    return ResponseEntity.ok(new RoleDTO(role));
  }

}
