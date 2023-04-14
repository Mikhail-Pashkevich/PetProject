package by.pashkevich.mikhail.config;

import by.pashkevich.mikhail.client.StatisticClient;
import by.pashkevich.mikhail.client.UserClient;
import by.pashkevich.mikhail.service.SettingService;
import feign.Feign;
import feign.Logger;
import feign.gson.GsonDecoder;
import feign.gson.GsonEncoder;
import feign.okhttp.OkHttpClient;
import feign.slf4j.Slf4jLogger;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;

@Configuration
@EnableScheduling
@RequiredArgsConstructor
public class AppConfig {
    private final SettingService settingService;
    @Value("${app.vars.user.host}")
    private String userHost;
    @Value("${app.vars.user.port}")
    private String userPort;
    @Value("${app.vars.statistic.host}")
    private String statisticHost;
    @Value("${app.vars.statistic.port}")
    private String statisticPort;

    @Bean
    public String getFixedRateSetting() {
        return settingService.getFixedRate();
    }

    @Bean
    public UserClient userClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(UserClient.class))
                .logLevel(Logger.Level.FULL)
                .target(UserClient.class, String.format("http://%s:%s/user", userHost, userPort));
    }

    @Bean
    public StatisticClient statisticClient() {
        return Feign.builder()
                .client(new OkHttpClient())
                .encoder(new GsonEncoder())
                .decoder(new GsonDecoder())
                .logger(new Slf4jLogger(StatisticClient.class))
                .logLevel(Logger.Level.FULL)
                .target(StatisticClient.class, String.format("http://%s:%s", statisticHost, statisticPort));
    }
}
