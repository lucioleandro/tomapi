package br.com.ecore.tom.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.TeamRepository;

@Service
public class TeamService {

  @Autowired
  private TeamRepository repository;

  @Transactional
  public Team create(Team team) {
    return repository.save(team);
  }

  @Transactional
  public Team update(Team team) {
    return repository.save(team);
  }

  public List<Team> createAll(List<Team> teams) {
    return repository.saveAll(teams);
  }

  public List<Team> findAll() {
    return repository.findAll();
  }

  public Team findByExternalId(UUID id) {
    return repository.findByUuid(id).orElseThrow(() -> new EntityNotFoundException(id, Team.class));
  }

}
