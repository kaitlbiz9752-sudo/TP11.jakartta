package ma.ens.security.spring_jwt_api;

import ma.ens.security.spring_jwt_api.entities.Role;
import ma.ens.security.spring_jwt_api.entities.User;
import ma.ens.security.spring_jwt_api.repositories.RoleRepository;
import ma.ens.security.spring_jwt_api.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.List;

@SpringBootApplication
public class SpringJwtApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringJwtApiApplication.class, args);
    }

    // Création d'un utilisateur admin / 1234 au démarrage
    @Bean
    CommandLineRunner initData(UserRepository userRepository,
                               RoleRepository roleRepository,
                               BCryptPasswordEncoder passwordEncoder) {
        return args -> {

            // Rôle ADMIN
            Role adminRole = roleRepository.findByName("ROLE_ADMIN");
            if (adminRole == null) {
                adminRole = new Role();
                adminRole.setName("ROLE_ADMIN");
                roleRepository.save(adminRole);
            }

            // Utilisateur admin
            if (userRepository.findByUsername("admin").isEmpty()) {
                User admin = new User();
                admin.setUsername("admin");
                admin.setPassword(passwordEncoder.encode("1234"));
                admin.setActive(true);
                admin.setRoles(List.of(adminRole));
                userRepository.save(admin);
            }
        };
    }
}
