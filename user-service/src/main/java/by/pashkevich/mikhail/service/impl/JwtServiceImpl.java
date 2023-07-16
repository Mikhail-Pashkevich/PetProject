package by.pashkevich.mikhail.service.impl;

import by.pashkevich.mikhail.exception.JwtException;
import by.pashkevich.mikhail.service.JwtService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private static final String SECRET = "secret";
    private static final long TOKEN_VALIDITY_IN_MILLIS = 30 * 60 * 1000;

    private Algorithm algorithm;
    private JWTVerifier verifier;

    @PostConstruct
    private void postConstruct() {
        algorithm = Algorithm.HMAC256(SECRET);
        verifier = JWT.require(algorithm).build();
    }

    @Override
    public String createJwt(String username) {
        return JWT.create()
                .withSubject(username)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(new Date().getTime() + TOKEN_VALIDITY_IN_MILLIS))
                .sign(algorithm);
    }

    @Override
    public String getUsername(String jwtToken) {
        try {
            if (isExpired(jwtToken)) {
                return null;
            }
            return verifier.verify(jwtToken).getSubject();
        } catch (JWTVerificationException e) {
            throw new JwtException(e.getMessage());
        }
    }

    private boolean isExpired(String jwtToken) {
        return new Date().after(verifier.verify(jwtToken).getExpiresAt());
    }
}
