package br.com.ecore.tom.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.domain.MemberShip;
import br.com.ecore.tom.exceptions.EntityNotFoundException;
import br.com.ecore.tom.repository.MemberShipRepository;

@Service
public class MemberShipService {

  @Autowired
  private MemberShipRepository repository;

  public MemberShip create(MemberShip memberShip) {
    return repository.save(memberShip);
  }

  public MemberShip findById(Integer externalId) {
    return repository.findById(externalId)
        .orElseThrow(() -> new EntityNotFoundException(externalId, MemberShip.class));
  }

}
