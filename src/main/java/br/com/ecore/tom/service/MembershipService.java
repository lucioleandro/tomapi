package br.com.ecore.tom.service;

import java.util.List;
import java.util.UUID;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.domain.Membership;
import br.com.ecore.tom.domain.dto.MembershipDTO;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.MembershipRepository;

@Service
public class MembershipService {

  @Autowired
  private MembershipRepository repository;

  @Transactional
  public Membership create(Membership membership) {
    membership.setUuid(UUID.randomUUID());
    return repository.save(membership);
  }

  public Membership findById(Integer externalId) {
    return repository.findById(externalId)
        .orElseThrow(() -> new EntityNotFoundException(externalId, Membership.class));
  }

  public Membership findByExternalId(UUID externalId) {
    return repository.findByUuid(externalId)
        .orElseThrow(() -> new EntityNotFoundException(externalId, Membership.class));
  }

  public List<MembershipDTO> findByRoleExternalId(UUID externalId) {
    return repository.findByRoleExternalId(externalId);
  }

}
