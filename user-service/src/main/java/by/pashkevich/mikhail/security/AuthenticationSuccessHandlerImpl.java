package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.security.process.token.ProcessToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Component
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private final Map<String, ProcessToken> processTokenMap;

    public AuthenticationSuccessHandlerImpl(List<ProcessToken> processTokenList) {
        this.processTokenMap = processTokenList
                .stream()
                .collect(Collectors.toMap(ProcessToken::getName, Function.identity()));
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        String key = ((OAuth2AuthenticationToken) authentication).getAuthorizedClientRegistrationId();
        DefaultOidcUser principal = (DefaultOidcUser) authentication.getPrincipal();
        String token = processTokenMap.get(key).process(principal);

        try {
            response.sendRedirect(String.format("/user/jwt/%s", token));
        } catch (Exception e) {
            log.info("USER IS NOT AUTHORIZED");
        }
    }
}
