package by.pashkevich.mikhail.security;

import by.pashkevich.mikhail.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
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
        String jwt = SecurityUtil.getAuthValue(request, SecurityUtil.RequestType.BEARER);

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
            SecurityUtil.setAuthentication(jwt, request);
            log.info("USER IS AUTHORIZED");
            session.setAttribute(JWT, jwt);
            filterChain.doFilter(request, response);
        } catch (Exception e) {
            log.info("USER IS NOT AUTHORIZED");
            filterChain.doFilter(request, response);
        }
    }
}
