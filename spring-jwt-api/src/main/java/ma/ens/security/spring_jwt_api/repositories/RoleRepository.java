package ma.ens.security.spring_jwt_api.repositories;

import ma.ens.security.spring_jwt_api.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Role findByName(String name);
}
