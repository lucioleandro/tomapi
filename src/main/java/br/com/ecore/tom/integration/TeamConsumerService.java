package br.com.ecore.tom.integration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import br.com.ecore.tom.domain.Member;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.domain.Team;
import br.com.ecore.tom.domain.dto.MemberDTO;
import br.com.ecore.tom.domain.dto.TeamDTO;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.service.MemberService;
import br.com.ecore.tom.service.MembershipService;
import br.com.ecore.tom.service.TeamService;

@Service
public class TeamConsumerService {

  private final String RESOURCE = "teams";

  @Value("${app.API_URL}")
  private String API_URL;

  @Autowired
  private TeamService teamService;

  @Autowired
  private MemberService memberService;
  
  @Autowired
  private MemberConsumerService memberConsumerService;

  @Autowired
  private MembershipService memberShipService;

  private RestTemplate restTemplate;

  @Autowired
  public TeamConsumerService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate =
        restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
  }
  
  public List<TeamDTO> fetchAll() {
    List<TeamDTO> teams = new ArrayList<>();
    TeamConsumerDTO[] fetchedTeams = this.restTemplate
        .getForEntity(API_URL + "/" + RESOURCE + "/", TeamConsumerDTO[].class).getBody();
    
    for(TeamConsumerDTO team : fetchedTeams) {
      TeamConsumerDTO completedTeam = this.findById(team.getId());
      if(completedTeam != null) {
        TeamDTO teamDto = new TeamDTO(completedTeam.getId(), completedTeam.getName());
        
        Member lead = memberConsumerService.findByExternalId(completedTeam.getTeamLeadId());
        MemberDTO leadDTO = new MemberDTO(lead);
        teamDto.setLead(leadDTO);
        
        for (UUID memberId : completedTeam.getTeamMemberIds()) {
          Member fetchedMember = memberConsumerService.findByExternalId(memberId);
          teamDto.addMember(new MemberDTO(fetchedMember));
        }
        teams.add(teamDto);
      }
    }
    return teams;
  }
  
  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public Team fetchAndSaveTeamById(UUID id) {
    TeamConsumerDTO teamDTO = findById(id);
    if (teamDTO == null) {
      throw new EntityNotFoundException(id, Team.class);
    }
    Member member = memberConsumerService.findByExternalId(teamDTO.getTeamLeadId());
    Team team = teamDTO.transformToTeam();
    team.setTeamLead(member);
    Team savedTeam = this.teamService.create(team);

    this.fetchMemberships(teamDTO, savedTeam);

    return savedTeam;
  }

  // TODO: Fazer calculo de algoritmo para analisar o quanto demora pra rodar
  @Transactional(propagation = Propagation.REQUIRED)
  public void fetchAndSaveTeams() {
    List<Team> listOfteams = this.teamService.findAll();

    TeamConsumerDTO[] teams = this.restTemplate
        .getForEntity(API_URL + "/" + RESOURCE + "/", TeamConsumerDTO[].class).getBody();

    List<Team> savedTeams = teamService.createAll(getOnlyNewTeams(teams, listOfteams));

    for (int i = 0; i < savedTeams.size(); i++) {
      Team savedTeam = savedTeams.get(i);
      TeamConsumerDTO completedTeamDTO = this.findById(savedTeam.getUuid());

      if (completedTeamDTO != null) {
        Member teamLead = memberService.findByExternalId(completedTeamDTO.getTeamLeadId());
        savedTeam.setTeamLead(teamLead);
        Team updatedTeam = teamService.update(savedTeam);

        this.fetchMemberships(completedTeamDTO, updatedTeam);
      }
    }
  }

  private void fetchMemberships(TeamConsumerDTO teamDTO, Team team) {
    for (UUID memberId : teamDTO.getTeamMemberIds()) {
      Member member = memberService.findByExternalId(memberId);
      Membership newShip = new Membership(member, team);
      newShip.setRole(new Role(1));
      memberShipService.create(newShip);
    }
  }

  private TeamConsumerDTO findById(UUID id) {
    return this.restTemplate.getForObject(API_URL + "/" + RESOURCE + "/" + id,
        TeamConsumerDTO.class);
  }

  private List<Team> getOnlyNewTeams(TeamConsumerDTO[] teams, List<Team> listOfteams) {
    Set<Team> setOfTeams = new HashSet<>(listOfteams);
    for (TeamConsumerDTO team : teams) {
      setOfTeams.add(team.transformToTeam());
    }
    return setOfTeams.stream().filter(team -> team.getId() == null).collect(Collectors.toList());
  }

}
