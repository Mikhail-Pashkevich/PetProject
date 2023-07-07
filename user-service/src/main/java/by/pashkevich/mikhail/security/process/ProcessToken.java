package by.pashkevich.mikhail.security.process;

import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;

public interface ProcessToken {

    String getName();

    String process(DefaultOidcUser user);
}
