package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.security.process.ProcessToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final Map<String, ProcessToken> functionMap;

    public AuthenticationSuccessHandlerImpl(@Qualifier(value = "processTokenMap")
                                            Map<String, ProcessToken> functionMap) {
        this.functionMap = functionMap;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        String key = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String token = functionMap.get(key).process(principal);

        try {
            response.sendRedirect(String.format("/user/jwt/%s", token));
        } catch (Exception e) {
            log.info("USER IS NOT AUTHORIZED");
        }
    }
}
