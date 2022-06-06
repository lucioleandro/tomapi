package br.com.ecore.tom.controller;

import java.net.URI;
import java.util.UUID;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.dto.MemberDTO;
import br.com.ecore.tom.exceptions.APIExceptionUtils;
import br.com.ecore.tom.service.MemberService;

@RestController
@RequestMapping("/member")
public class MemberController {

  @Autowired
  private MemberService service;

  @PostMapping
  public ResponseEntity<Member> cadastraMember(@Valid @RequestBody Member member,
      UriComponentsBuilder uriBuilder) {
    APIExceptionUtils.assertValidPost(member.getId());
    Member memberSalva = service.create(member);
    URI uri = uriBuilder.path("/member/{id}").buildAndExpand(memberSalva.getId()).toUri();
    return ResponseEntity.created(uri).body(memberSalva);
  }

  @PatchMapping("assign/{memberExternalId}/{roleExternalId}")
  public ResponseEntity<MemberDTO> assignRoleToMember(@PathVariable UUID memberExternalId,
      @PathVariable UUID roleExternalId) {
    Member updatedMember = service.assignRole(memberExternalId, roleExternalId);
    return ResponseEntity.ok(new MemberDTO(updatedMember));
  }

  @GetMapping("/{id}")
  public ResponseEntity<MemberDTO> buscaMemberPorId(@PathVariable Integer id) {
    Member member = service.findById(id);
    return ResponseEntity.ok(new MemberDTO(member));
  }

}
