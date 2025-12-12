package ru.swat1x.lensesscheduleservice.config;

import lombok.SneakyThrows;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;
import ru.swat1x.lensesscheduleservice.model.UpdateNotificationModel;

import java.net.InetAddress;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author swat1x (Vadim Smyshlyaev)
 * Created at 12.12.2025
 */
@Configuration
public class KafkaConfiguration {

    private static final String NOTIFICATION_TOPIC = "notifications";

    @Bean
    @SneakyThrows
    public ProducerFactory<UUID, UpdateNotificationModel> producerFactory(@Value("${schedule.kafka.host}") String kafkaHost) {
        Map<String, Object> props = new HashMap<>();
        props.put(
                ProducerConfig.CLIENT_ID_CONFIG,
                InetAddress.getLocalHost().getHostName()
        );
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

        return new DefaultKafkaProducerFactory<>(props);
    }

    @Bean
    public KafkaTemplate<UUID, UpdateNotificationModel> kafkaTemplate(ProducerFactory<UUID, UpdateNotificationModel> producerFactory) {
        var kafkaTemplate = new KafkaTemplate<>(producerFactory);
        kafkaTemplate.setDefaultTopic(NOTIFICATION_TOPIC);
        return kafkaTemplate;
    }

    @Bean
    public KafkaAdmin admin(@Value("${schedule.kafka.host}") String kafkaHost) {
        Map<String, Object> configs = new HashMap<>();
        configs.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaHost);
        return new KafkaAdmin(configs);
    }

    @Bean
    public NewTopic notificationTopic() {
        return TopicBuilder.name(NOTIFICATION_TOPIC)
                .partitions(3)
//                .replicas(2)
                .compact()
                .build();
    }

}
