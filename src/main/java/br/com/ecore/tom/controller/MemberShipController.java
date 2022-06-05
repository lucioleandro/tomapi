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
import br.com.ecore.tom.domain.MemberShip;
import br.com.ecore.tom.exceptions.APIExceptionUtils;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.service.MemberShipService;

@RestController
@RequestMapping("/membership")
public class MemberShipController {

  @Autowired
  private MemberShipService service;

  @PostMapping
  public ResponseEntity<MemberShip> cadastraMember(@Valid @RequestBody MemberShip memberShip,
      UriComponentsBuilder uriBuilder) {
    APIExceptionUtils.assertValidPost(memberShip.getId());
    MemberShip memberSalva = service.create(memberShip);
    URI uri = uriBuilder.path("/member/{id}").buildAndExpand(memberSalva.getId()).toUri();
    return ResponseEntity.created(uri).body(memberSalva);
  }

  @GetMapping("/{id}")
  public ResponseEntity<MemberShip> buscaMemberPorId(@PathVariable Integer id) {
    MemberShip memberShip =
        service.findById(id).orElseThrow(() -> new EntityNotFoundException(id, MemberShip.class));
    return ResponseEntity.ok(memberShip);
  }

  @DeleteMapping("/{id}")
  public void remove(@PathVariable Integer id) {
    service.remove(id);
  }
}
