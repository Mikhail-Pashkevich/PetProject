package by.pashkevich.mikhail.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;

public class KafkaConfig {

    public static final String STATISTIC_TOPIC_NAME = "statistics";

    @Bean
    public NewTopic statistics() {
        return new NewTopic(STATISTIC_TOPIC_NAME, 1, (short) 1);
    }
}
