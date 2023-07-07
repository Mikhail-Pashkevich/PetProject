package by.pashkevich.mikhail.security.process.impl;

import by.pashkevich.mikhail.security.process.ProcessToken;
import lombok.Getter;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;


@Component
public class CustomAuthServerProcessToken implements ProcessToken {

    @Getter
    private final String name = "custom-client-oidc";

    @Override
    public String process(DefaultOidcUser user) {
        return user.getIdToken().getTokenValue();
    }
}
