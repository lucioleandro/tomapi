package br.com.ecore.tom.service;

import java.util.Optional;
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

  public Optional<MemberShip> findById(Integer id) {
    return repository.findById(id);
  }

  public void remove(Integer id) {
    MemberShip memberShip = getMemberByIdOrElseThrow(id);
    repository.deleteById(memberShip.getId());
  }

  private MemberShip getMemberByIdOrElseThrow(Integer id) {
    return this.repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException(id, MemberShip.class));
  }

}
