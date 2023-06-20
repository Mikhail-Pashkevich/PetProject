package by.pashkevich.mikhail.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;


@Configuration
@RequiredArgsConstructor
public class SecurityFilterConfig {
    private final AuthenticationSuccessHandler authenticationSuccessHandler;

    /**
     * There 2 problems:
     * 1. first login always fails;
     * 2. after authorization by any method, the second one always fails.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors().disable()
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/user/**").permitAll()
                .antMatchers("/oauth/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().disable()
                .oauth2Login().successHandler(authenticationSuccessHandler)
                .and()
                .build();
    }
}
