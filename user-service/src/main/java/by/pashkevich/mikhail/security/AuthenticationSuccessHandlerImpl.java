package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {
    private final JwtService jwtService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        Map<String, Object> claims = ((DefaultOidcUser) authentication.getPrincipal()).getIdToken().getClaims();
        if (!Boolean.TRUE.equals(claims.get("email_verified"))) {
            return;
        }
        //TODO: check is exists
        String jwt = jwtService.createJwt(claims.get("email").toString());

        try {
            response.sendRedirect(String.format("/user/jwt/%s", jwt));
        } catch (Exception e) {
            log.info("USER IS NOT AUTHORIZED");
        }
    }
}
