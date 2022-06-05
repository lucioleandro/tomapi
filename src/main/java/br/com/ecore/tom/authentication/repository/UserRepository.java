package br.com.ecore.tom.authentication.repository;


import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import br.com.ecore.tom.authentication.domain.User;

public interface UserRepository extends JpaRepository<User, Long> {

  @Query("select m from User m where m.login = :username")
  User findByName(String username);

  Optional<User> findByLogin(String login);

}
