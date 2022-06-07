package br.com.ecore.tom.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.dto.MembershipDTO;
import br.com.ecore.tom.service.MembershipService;

@RestController
@RequestMapping("/membership")
public class MembershipController {

  @Autowired
  private MembershipService service;

  @GetMapping("/{id}")
  public ResponseEntity<Membership> buscaMemberPorId(@PathVariable Integer externalId) {
    Membership memberShip = service.findById(externalId);
    return ResponseEntity.ok(memberShip);
  }

  @GetMapping("role/{roleExternalId}")
  public ResponseEntity<List<MembershipDTO>> lookupByRole(@PathVariable UUID roleExternalId) {
    List<MembershipDTO> memberShips = service.findByRoleExternalId(roleExternalId);
    return ResponseEntity.ok(memberShips);
  }

}
