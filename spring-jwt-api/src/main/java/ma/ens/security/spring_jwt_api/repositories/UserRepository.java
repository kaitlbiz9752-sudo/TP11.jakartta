package ma.ens.security.spring_jwt_api.repositories;

import ma.ens.security.spring_jwt_api.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);
}
