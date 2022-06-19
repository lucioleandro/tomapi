package br.com.ecore.tom.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.dto.MembershipDTO;
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

  @GetMapping("/{externalId}")
  public ResponseEntity<MembershipDTO> lookupByExternalId(@PathVariable UUID externalId) {
    Membership memberShip = service.findByExternalId(externalId);
    return ResponseEntity.ok(new MembershipDTO(memberShip));
  }

  @GetMapping
  public ResponseEntity<Page<MembershipDTO>> lookupAll(Pageable pageable) {
    Page<MembershipDTO> pageShips = service.findAll(pageable);
    return ResponseEntity.ok(pageShips);
  }

  @GetMapping("role/{roleExternalId}")
  public ResponseEntity<List<MembershipDTO>> lookupByRole(@PathVariable UUID roleExternalId) {
    List<MembershipDTO> memberShips = service.findByRoleExternalId(roleExternalId);
    return ResponseEntity.ok(memberShips);
  }

}
