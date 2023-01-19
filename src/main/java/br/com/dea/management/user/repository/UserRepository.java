package br.com.dea.management.user.repository;

import br.com.dea.management.user.domain.User;
import jakarta.persistence.NamedQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    public Optional<User> findByName(String name);

    @Query("SELECT u FROM User u WHERE email = :email")
    public  Optional<User> findByEmail(String email);
}
