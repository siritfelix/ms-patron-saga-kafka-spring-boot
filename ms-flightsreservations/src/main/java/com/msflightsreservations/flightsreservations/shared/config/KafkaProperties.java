package com.msflightsreservations.flightsreservations.shared.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "kafka-config")
public class KafkaProperties {
    private Integer attempts;
    private ProducerProperties producer;
    private ConsumerProperties consumer;

    @Getter
    @Setter
    public static class ProducerProperties {
        private String topic;
        private String topicReturn;
        private String bootstrapServers;
        private String keySerializer;
        private String valueSerializer;
    }

    @Getter
    @Setter
    public static class ConsumerProperties {
        private String topic;
        private String bootstrapServers;
        private String groupId;
        private String keyDeserializer;
        private String valueDeserializer;
    }
}
