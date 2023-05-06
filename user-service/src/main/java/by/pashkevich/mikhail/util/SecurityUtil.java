package by.pashkevich.mikhail.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@UtilityClass
public class SecurityUtil {
    public String getAuthValue(HttpServletRequest request, RequestType type) {
        String headerAuth = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith(type.getType())) {
            return headerAuth.substring(type.getType().length());
        }
        return null;
    }

    public void setAuthentication(String jwt, HttpServletRequest request) {
        AbstractAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(jwt, null, null);
        authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    @Getter
    @RequiredArgsConstructor
    public enum RequestType {
        BEARER("Bearer ");
        private final String type;
    }
}
