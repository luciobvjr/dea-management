package br.com.dea.management.user.repository;

import br.com.dea.management.user.domain.User;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findById(Long id);

    @Query("SELECT u FROM User u WHERE name = :name")
    Optional<User> findByName(String name);

    @Query("SELECT u FROM User u WHERE email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE linkedin = :linkedin")
    Optional<User> findByLinkedin(String linkedin);
}
