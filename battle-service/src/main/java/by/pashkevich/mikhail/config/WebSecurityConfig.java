package by.pashkevich.mikhail.config;

import by.pashkevich.mikhail.security.JwtAuthenticationManager;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final JwtAuthenticationManager jwtAuthenticationManager;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .authorizeRequests(urlRegistry -> urlRegistry.anyRequest().authenticated())
                .oauth2ResourceServer(resourceServer -> resourceServer
                        .jwt(jwtConfigurer -> jwtConfigurer.
                                authenticationManager(jwtAuthenticationManager)
                        )
                )
                .build();
    }
}
