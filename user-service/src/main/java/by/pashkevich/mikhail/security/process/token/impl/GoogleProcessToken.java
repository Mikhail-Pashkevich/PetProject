package by.pashkevich.mikhail.security.process.token.impl;

import by.pashkevich.mikhail.entity.User;
import by.pashkevich.mikhail.security.process.token.ProcessToken;
import by.pashkevich.mikhail.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class GoogleProcessToken implements ProcessToken {

    private static final String EMAIL = "email";

    @Getter
    private final String name = "google";
    private final UserService userService;

    @Override
    public String process(DefaultOidcUser oidcUser) {
        Map<String, Object> claims = oidcUser.getClaims();
        String email = claims.get(EMAIL).toString();
        if (!userService.existsByUsername(email)) {
            userService.create(new User(email));
        }
        return oidcUser.getIdToken().getTokenValue();
    }
}
