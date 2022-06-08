package br.com.ecore.tom.authentication.service;

import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import br.com.ecore.tom.authentication.domain.User;
import br.com.ecore.tom.authentication.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

  @Autowired
  private UserRepository usuarioRepository;

  public UserDetails loadUserByUsername(String username) {
    User usuario = usuarioRepository.findByName(username);

    if (usuario == null) {
      throw new UsernameNotFoundException("User " + username + " not found");
    }

    return usuario;
  }

  public User create(User usuario) {
    return usuarioRepository.saveAndFlush(usuario);
  }

  public User atualiza(User usuario) {
    return usuarioRepository.save(usuario);
  }

  public List<User> findAll() {
    return usuarioRepository.findAll();
  }

  public Optional<User> findById(Long id) {
    return usuarioRepository.findById(id);
  }

  public Optional<User> findByLogin(String login) {
    return usuarioRepository.findByLogin(login);
  }

  public void remove(User usuario) {
    usuarioRepository.delete(usuario);
  }

}
