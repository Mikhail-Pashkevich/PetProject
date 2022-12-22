package by.pashkevich.mikhail.config;

import by.pashkevich.mikhail.service.SettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {
    private final SettingService settingService;

    @Bean
    public String getFixedRateSetting() {
        return settingService.getFixedRate();
    }
}
