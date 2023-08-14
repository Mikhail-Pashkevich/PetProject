package by.pashkevich.mikhail.service;

public interface JwtService {
    String createJwt(String username);

    String getUsername(String jwtToken);
}
