package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.client.UserClient;
import by.pashkevich.mikhail.model.dto.UserDto;
import by.pashkevich.mikhail.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String USERNAME = "username";

    private final UserClient userClient;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String jwt = getJwt(request);

        if (jwt == null) {
            log.info("REQUEST DON'T HAVE JWT");
            filterChain.doFilter(request, response);
            return;
        }

        HttpSession session = request.getSession();
        String username = (String) session.getAttribute(USERNAME);
        if (username != null) {
            filterChain.doFilter(request, response);
            return;
        }

        UserDto userDto = userClient.getUser(request.getHeader(HttpHeaders.AUTHORIZATION));
        if (userDto != null) {
            authorizeUser(userDto, request);
            session.setAttribute(USERNAME, userDto.getLogin());
            filterChain.doFilter(request, response);
            return;
        }

        log.info("USER IS NOT AUTHORIZED");
        filterChain.doFilter(request, response);
    }

    private String getJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private void authorizeUser(UserDto userDto, HttpServletRequest request) {
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                new User(userDto.getId(), userDto.getLogin()),
                null,
                userDto.getRoles()
                        .stream()
                        .map(role -> (GrantedAuthority) () -> "ROLE_" + role)
                        .collect(Collectors.toSet())
        );
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("USER IS AUTHORIZED");
    }
}
