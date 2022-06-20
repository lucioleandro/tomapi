package br.com.ecore.tom.integration;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
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
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.MembershipRepository;
import br.com.ecore.tom.repository.TeamRepository;
import br.com.ecore.tom.service.MemberService;

@Service
public class MembershipConsumerService {

  private final String RESOURCE = "teams";

  @Value("${app.API_URL}")
  private String API_URL;

  @Autowired
  private TeamRepository teamRepository;

  @Autowired
  private MemberService memberService;

  @Autowired
  private MembershipRepository membershipRepository;

  private RestTemplate restTemplate;

  @Autowired
  public MembershipConsumerService(RestTemplateBuilder restTemplateBuilder) {
    this.restTemplate =
        restTemplateBuilder.errorHandler(new RestTemplateResponseErrorHandler()).build();
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  public void fetchMembership(UUID teamExternalId, UUID memberExternalId) {
    TeamConsumerDTO teamDTO = findById(teamExternalId);
    if (teamDTO == null) {
      throw new EntityNotFoundException(teamExternalId, Team.class);
    }

    Optional<Team> optionalTeam = teamRepository.findByUuid(teamExternalId);
    if (optionalTeam.isPresent()) {
      List<Membership> ships = membershipRepository.findByTeamUuid(teamExternalId);
      List<Member> membersToSave = extractNewMembersToSave(teamDTO, ships);

      List<Membership> newShips = new ArrayList<>();

      for (Member m : membersToSave) {
        Member member = memberService.findByExternalId(m.getUuid());
        newShips.add(new Membership(UUID.randomUUID(), member, optionalTeam.get()));
      }
      membershipRepository.saveAll(newShips);

    } else {
      Member member = memberService.findByExternalId(teamDTO.getTeamLeadId());
      Team team = teamDTO.transformToTeam();
      team.setTeamLead(member);
      Team savedTeam = this.teamRepository.save(team);

      this.fetchMemberships(teamDTO, savedTeam);

    }
  }

  private List<Member> extractNewMembersToSave(TeamConsumerDTO teamDTO, List<Membership> ships) {
    List<Member> members = new ArrayList<>();

    for (Membership ship : ships) {
      members.add(ship.getMember());
    }

    Set<Member> membersSet = new HashSet<>(members);

    for (UUID memberId : teamDTO.getTeamMemberIds()) {
      membersSet.add(new Member(memberId));
    }

    return membersSet.stream().filter(team -> team.getId() == null).collect(Collectors.toList());
  }

  private TeamConsumerDTO findById(UUID id) {
    return this.restTemplate.getForObject(API_URL + "/" + RESOURCE + "/" + id,
        TeamConsumerDTO.class);
  }

  private void fetchMemberships(TeamConsumerDTO teamDTO, Team team) {
    for (UUID memberId : teamDTO.getTeamMemberIds()) {
      Member member = memberService.findByExternalId(memberId);
      Membership newShip = new Membership(member, team);
      newShip.setRole(new Role(1));
      membershipRepository.save(newShip);
    }
  }


}
