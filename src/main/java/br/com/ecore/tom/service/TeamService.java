package br.com.ecore.tom.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.repository.TeamRepository;

@Service
public class TeamService {

  @Autowired
  private TeamRepository repository;

  public Team create(Team team) {
    return repository.save(team);
  }

  public List<Team> findAll() {
    return repository.findAll();
  }

  public Optional<Team> findById(Long id) {
    return repository.findById(id);
  }

}
