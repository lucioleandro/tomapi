package br.com.ecore.tom.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.Role;
import br.com.ecore.tom.domain.dto.MembershipDTO;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.integration.MembershipConsumerService;
import br.com.ecore.tom.repository.MembershipRepository;

@Service
public class MembershipService {

  @Autowired
  private MembershipRepository repository;

  @Autowired
  private MembershipConsumerService membershipConsumerService;

  @Autowired
  private RoleService roleService;

  @Transactional
  public Membership create(Membership membership) {
    membership.setUuid(UUID.randomUUID());
    return repository.save(membership);
  }

  @Transactional
  public Membership assignRole(UUID teamExternalId, UUID memberExternalId, UUID roleExternalId) {
    Role role = null;
    if (roleExternalId == null) {
      role = roleService.findByName("Developer");
    } else {
      role = roleService.findByExternalId(roleExternalId);
    }

    Membership membership = findByMembership(teamExternalId, memberExternalId);
    membership.setRole(role);

    return repository.save(membership);
  }

  public Page<MembershipDTO> findAll(Pageable pageable) {
    Page<Membership> ships = repository.findAll(pageable);
    return ships.map(MembershipDTO::new);
  }

  public Membership findByExternalId(UUID externalId) {
    return repository.findByUuid(externalId)
        .orElseThrow(() -> new EntityNotFoundException(externalId, Membership.class));
  }


  /**
   * This method firstly tries to find a register in the database, If not found the method will try
   * to fetch the data from the other application and save it in the database, if don’t fetch a
   * runtime exception will be thrown
   * 
   * @param teamExternalId
   * @param memberExternalId
   * @throws EntityNotFoundException
   * @return Optional of Membership
   */
  public Membership findByMembership(UUID teamExternalId, UUID memberExternalId) {
    Optional<Membership> optionalMembership =
        repository.findByTeamUuidAndMemberUuid(teamExternalId, memberExternalId);
    if (optionalMembership.isPresent()) {
      return optionalMembership.get();
    }
    List<Membership> fetchedShips = membershipConsumerService.fetchMembership(teamExternalId, memberExternalId);
    Membership targetShip = fetchedShips.stream()
        .filter(s -> s.getTeam().getUuid().equals(teamExternalId) 
            && s.getMember().getUuid().equals(memberExternalId))
        .collect(Collectors.toList()).get(0);
    if(targetShip == null) {
      throw new EntityNotFoundException(
          "Thre is no membership with this combination of team and member", Membership.class);
    }
    return targetShip;
  }

  public List<MembershipDTO> findByRoleExternalId(UUID externalId) {
    return repository.findByRoleUuid(externalId);
  }

}
