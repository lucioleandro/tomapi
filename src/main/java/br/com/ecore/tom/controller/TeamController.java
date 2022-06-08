package br.com.ecore.tom.controller;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.service.TeamService;

@RestController
@RequestMapping("/team")
public class TeamController {

  @Autowired
  private TeamService service;

  @GetMapping
  public ResponseEntity<List<Team>> lookupAll() {
    List<Team> tarefas = service.findAll();
    return ResponseEntity.ok(tarefas);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Team> lookupByExternald(@PathVariable UUID id) {
    Team teams = service.findByeExternalId(id)
        .orElseThrow(() -> new EntityNotFoundException(id, Team.class));
    return ResponseEntity.ok(teams);
  }

}
