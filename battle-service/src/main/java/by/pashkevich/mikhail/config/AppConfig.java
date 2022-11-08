package by.pashkevich.mikhail.config;

import by.pashkevich.mikhail.service.ScheduleSettingService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {
    private final ScheduleSettingService scheduleSettingService;

    @Bean
    public String getFixedRateSetting() {
        return scheduleSettingService.getFixedRate();
    }
}
