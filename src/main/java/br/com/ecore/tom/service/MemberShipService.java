package br.com.ecore.tom.service;

import java.util.List;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.domain.MemberShip;
import br.com.ecore.tom.domain.dto.MemberShipDTO;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.MemberShipRepository;

@Service
public class MemberShipService {

  @Autowired
  private MemberShipRepository repository;

  public MemberShip create(MemberShip memberShip) {
    memberShip.setUuid(UUID.randomUUID());
    return repository.save(memberShip);
  }

  public MemberShip findById(Integer externalId) {
    return repository.findById(externalId)
        .orElseThrow(() -> new EntityNotFoundException(externalId, MemberShip.class));
  }

  public List<MemberShipDTO> findByRoleExternalId(UUID externalId) {
    return repository.findByRoleExternalId(externalId);
  }

}
