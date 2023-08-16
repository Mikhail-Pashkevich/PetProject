package by.pashkevich.mikhail.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig {

    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().disable()
                .csrf().disable()
                .formLogin().disable()
                .authorizeRequests(urlRegistry -> urlRegistry
                        .antMatchers("/user/**").permitAll()
                        .antMatchers("/oauth/**").permitAll()
                        .anyRequest().authenticated()
                )
                .oauth2Login(oAuth2Login -> oAuth2Login.successHandler(authenticationSuccessHandler))
                .build();
    }
}
