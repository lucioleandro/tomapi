package br.com.ecore.tom.controller;

import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.dto.MemberDTO;
import br.com.ecore.tom.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {

  @Autowired
  private MemberService service;

  @PatchMapping("assign/{memberExternalId}/{roleExternalId}")
  public ResponseEntity<MemberDTO> assignRoleToMember(@PathVariable UUID memberExternalId,
      @PathVariable UUID roleExternalId) {
    Member updatedMember = service.assignRole(memberExternalId, roleExternalId);
    return ResponseEntity.ok(new MemberDTO(updatedMember));
  }

  @GetMapping("/{externalId}")
  public ResponseEntity<MemberDTO> lookupByExternalId(@PathVariable UUID externalId) {
    Member member = service.findByExternalId(externalId);
    return ResponseEntity.ok(new MemberDTO(member));
  }

}
