package br.com.ecore.tom.controller;

import java.net.URI;
import java.util.List;
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
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.exceptions.APIExceptionUtils;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.service.TeamService;

@RestController
@RequestMapping("/team")
public class TeamController {

  @Autowired
  private TeamService service;

  @PostMapping
  public ResponseEntity<Team> cadastraTeam(@RequestBody Team team,
      UriComponentsBuilder uriBuilder) {
    APIExceptionUtils.assertValidPost(team.getId());
    // TODO AJEITAR ESSA URL
    Team teamSalva = service.create(team);
    URI uri = uriBuilder.path("/team/{id}").buildAndExpand(teamSalva.getId()).toUri();
    return ResponseEntity.created(uri).body(teamSalva);
  }

  @GetMapping
  public ResponseEntity<List<Team>> buscaLista() {
    List<Team> tarefas = service.findAll();
    return ResponseEntity.ok(tarefas);
  }

  @GetMapping("/{id}")
  public ResponseEntity<Team> buscaTeamPorId(@PathVariable Integer id) {
    Team teams =
        service.findById(id).orElseThrow(() -> new EntityNotFoundException(id, Team.class));
    return ResponseEntity.ok(teams);
  }

  @DeleteMapping("/{id}")
  public void remove(@PathVariable Long id) {
    // service.remove(id);
  }
}
