package br.com.ecore.tom.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ecore.tom.domain.MemberShip;
import br.com.ecore.tom.service.MemberShipService;

@RestController
@RequestMapping("/membership")
public class MemberShipController {

  @Autowired
  private MemberShipService service;

  @GetMapping("/{id}")
  public ResponseEntity<MemberShip> buscaMemberPorId(@PathVariable Integer externalId) {
    MemberShip memberShip = service.findById(externalId);
    return ResponseEntity.ok(memberShip);
  }

}
