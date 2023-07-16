package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.model.entity.User;
import by.pashkevich.mikhail.service.JwtService;
import by.pashkevich.mikhail.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationSuccessHandlerImpl implements AuthenticationSuccessHandler {

    private static final String EMAIL = "email";
    private static final String SUB = "sub";

    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) {
        Map<String, Object> claims = ((DefaultOidcUser) authentication.getPrincipal()).getIdToken().getClaims();

        String username = Optional.ofNullable(claims.get(EMAIL))
                .map(Object::toString)
                .map(login -> {
                    userService.create(new User(login));
                    return login;
                })
                .orElse(claims.get(SUB).toString());

        String jwt = jwtService.createJwt(username);

        try {
            response.sendRedirect(String.format("/user/jwt/%s", jwt));
        } catch (Exception e) {
            log.info("USER IS NOT AUTHORIZED");
        }
    }
}
