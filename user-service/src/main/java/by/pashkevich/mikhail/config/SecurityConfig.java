package by.pashkevich.mikhail.config;

import by.pashkevich.mikhail.security.process.ProcessToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean("processTokenMap")
    public Map<String, ProcessToken> processToken(List<ProcessToken> processTokenList) {
        return processTokenList.stream().collect(Collectors.toMap(ProcessToken::getName, Function.identity()));
    }
}
