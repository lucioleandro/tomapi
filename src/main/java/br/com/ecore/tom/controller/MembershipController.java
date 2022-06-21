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
import br.com.ecore.tom.domain.dto.TeamDTO;
import br.com.ecore.tom.service.MembershipService;
import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/memberships")
public class MembershipController {

  @Autowired
  private MembershipService service;

  @PatchMapping("assign/{teamExternalId}/{memberExternalId}/{roleExternalId}")
  @ApiOperation(value = "Assign a role to a membership",
      notes = "Given a membership defined by a team and a member and a role, assign the given role to the given membership and return its DTO")
  public ResponseEntity<MembershipDTO> assignRoleToMember(@PathVariable UUID teamExternalId,
      @PathVariable UUID memberExternalId, @PathVariable(required = false) UUID roleExternalId) {
    Membership updatedMembership =
        service.assignRole(teamExternalId, memberExternalId, roleExternalId);
    return ResponseEntity.ok(new MembershipDTO(updatedMembership));
  }

  @PatchMapping("assign/{teamExternalId}/{memberExternalId}")
  @ApiOperation(value = "Assign a default role to a membership",
      notes = "Given a membership defined by a team and a member, assign a default role to the membership and return its DTO")
  public ResponseEntity<MembershipDTO> assignRoleToMember(@PathVariable UUID teamExternalId,
      @PathVariable UUID memberExternalId) {
    Membership updatedMembership = service.assignRole(teamExternalId, memberExternalId, null);
    return ResponseEntity.ok(new MembershipDTO(updatedMembership));
  }

  @GetMapping("/{teamExternalId}/{memberExternalId}")
  @ApiOperation(value = "find a membership given teamID and memberID",
      notes = "Find a membership by its tem and member ID's. "
          + "First it tries to find from tom database, and if needed it tries to find in the other application")
  public ResponseEntity<MembershipDTO> lookupByAMembership(@PathVariable UUID teamExternalId,
      @PathVariable UUID memberExternalId) {
    Membership memberShip = service.findByMembership(teamExternalId, memberExternalId);
    return ResponseEntity.ok(new MembershipDTO(memberShip));
  }

  @GetMapping("/{teamExternalId}")
  @ApiOperation(value = "get all memberships from a team given the team ID",
      notes = "Find all memberships by its ID. "
          + "First it tries to find from tom database, and if needed it tries to find in the other application")
  public ResponseEntity<List<MembershipDTO>> lookupByATeam(@PathVariable UUID teamExternalId) {
    List<MembershipDTO> ships = service.findByATeam(teamExternalId);
    return ResponseEntity.ok(ships);
  }

  @GetMapping
  @ApiOperation(value = "Get all registers in tom's database",
      notes = "Get all registers in tom's database, it doesn't look in the other application")
  public ResponseEntity<Page<MembershipDTO>> lookupAll(Pageable pageable) {
    Page<MembershipDTO> pageShips = service.findAll(pageable);
    return ResponseEntity.ok(pageShips);
  }

  @GetMapping("/fetch")
  @ApiOperation(value = "Get all registers from the other application",
      notes = "Get all registers directly from the other application")
  public ResponseEntity<List<TeamDTO>> lookupAllFromTheOtherApplication() {
    List<TeamDTO> ships = service.fetchAllFromOtherApplication();
    return ResponseEntity.ok(ships);
  }

  @GetMapping("role/{roleExternalId}")
  @ApiOperation(value = "Get all memberships given a role",
      notes = "Get a role, it will return all memberships in any team with the role associated")
  public ResponseEntity<List<MembershipDTO>> lookupByRole(@PathVariable UUID roleExternalId) {
    List<MembershipDTO> memberShips = service.findByRoleExternalId(roleExternalId);
    return ResponseEntity.ok(memberShips);
  }

}
