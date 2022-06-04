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
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.exceptions.APIExceptionUtils;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
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

  @GetMapping("/{id}")
  public ResponseEntity<Member> buscaMemberPorId(@PathVariable String id) {
    Member member =
        service.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Member.class));
    return ResponseEntity.ok(member);
  }

  @DeleteMapping("/{id}")
  public void remove(@PathVariable String id) {
    service.remove(id);
  }
}
