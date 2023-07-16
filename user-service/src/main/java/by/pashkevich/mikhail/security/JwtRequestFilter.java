package by.pashkevich.mikhail.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtRequestFilter extends OncePerRequestFilter {
    private static final String JWT = "jwt";

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
        Object attribute = session.getAttribute(JWT);
        if (attribute != null) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            setJwt(jwt, request);
            session.setAttribute(JWT, jwt);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.info("USER IS NOT AUTHORIZED");
            filterChain.doFilter(request, response);
        }
    }

    private String getJwt(HttpServletRequest request) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
            return headerAuth.substring(7);
        }
        return null;
    }

    private void setJwt(String jwt, HttpServletRequest request) {
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwt, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("USER IS AUTHORIZED");
    }
}
