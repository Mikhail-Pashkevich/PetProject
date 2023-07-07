package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.model.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {

    private static final String ID = "user_id";
    private static final String LOGIN = "login";
    private static final String ROLES = "roles";

    private final JwtDecoder jwtDecoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = ((BearerTokenAuthenticationToken) authentication).getToken();
        Jwt jwt = jwtDecoder.decode(token);
        Map<String, Object> claims = jwt.getClaims();

        Long id = (Long) claims.get(ID);
        String login = claims.get(LOGIN).toString();
        List<String> roles = (List<String>) claims.get(ROLES);

        return new UsernamePasswordAuthenticationToken(
                new User(id, login),
                null,
                roles.stream().map(role -> (GrantedAuthority) () -> role).collect(Collectors.toSet()));
    }
}
