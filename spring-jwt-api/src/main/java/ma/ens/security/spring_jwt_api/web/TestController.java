package ma.ens.security.spring_jwt_api.web;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/user/profile")
    public Map<String, Object> profile(Authentication authentication) {

        String username = authentication.getName();
        List<String> roles = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        return Map.of(
                "username", username,
                "roles", roles
        );
    }

    @GetMapping("/admin/dashboard")
    public Map<String, String> admin() {
        return Map.of("message", "Bienvenue ADMIN");
    }
}
