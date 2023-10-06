package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.entity.User;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {

    private static final String EMAIL = "email";

    private final JwtDecoder jwtDecoder;
    private final UserService userService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = ((BearerTokenAuthenticationToken) authentication).getToken();
        Jwt jwt = jwtDecoder.decode(token);
        Map<String, Object> claims = jwt.getClaims();

        User user = userService.getByUsername(claims.get(EMAIL).toString());

        return new UsernamePasswordAuthenticationToken(
                user,
                null,
                user.getRoles()
                        .stream()
                        .map(role -> (GrantedAuthority) () -> "ROLE_" + role.getName().name())
                        .collect(Collectors.toSet())
        );
    }
}
