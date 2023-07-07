package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.endpoint.OidcParameterNames;
import org.springframework.security.oauth2.server.authorization.token.JwtEncodingContext;
import org.springframework.security.oauth2.server.authorization.token.OAuth2TokenCustomizer;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class OAuth2TokenCustomizerImpl implements OAuth2TokenCustomizer<JwtEncodingContext> {

    private static final String ID = "user_id";
    private static final String LOGIN = "login";
    private static final String ROLES = "roles";

    private final UserService userService;

    @Override
    public void customize(JwtEncodingContext context) {
        if (!context.getTokenType().getValue().equals(OidcParameterNames.ID_TOKEN)) {
            return;
        }
        Authentication principal = context.getPrincipal();

        User user = userService.getByLogin(principal.getName());
        context.getClaims()
                .claim(ID, user.getId())
                .claim(LOGIN, user.getLogin())
                .claim(ROLES, user.getRoles()
                        .stream()
                        .map(role -> "ROLE_" + role.getName().name())
                        .toList());
    }
}
