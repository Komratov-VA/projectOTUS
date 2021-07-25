package com.otus.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.kafka.PostClientsDeserializer;
import com.otus.kafka.model.PostClients;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
@RequiredArgsConstructor
public class KafkaConfiguration {

    private final KafkaProperties kafkaProperties;

    @Bean
    public DefaultKafkaConsumerFactory<String, PostClients> kafkaConsumerFactory(ConcurrentKafkaListenerContainerFactory<String, PostClients> factory) {
        final Map<String, Object> configs = new HashMap<>(factory.getConsumerFactory().getConfigurationProperties());
        configs.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        configs.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, PostClientsDeserializer.class);
        DefaultKafkaConsumerFactory<String, PostClients> consumerFactory = new DefaultKafkaConsumerFactory<>(configs);
        factory.setConsumerFactory(consumerFactory);
        factory.setBatchListener(true);
        return consumerFactory;
    }

    @Bean
    public ProducerFactory<String, PostClients> pushKafkaProducerFactory() {
        DefaultKafkaProducerFactory<String, PostClients> kafkaProducerFactory = new DefaultKafkaProducerFactory<>(kafkaProperties.buildProducerProperties());
        kafkaProducerFactory.setValueSerializer(new JsonSerializer<>(new ObjectMapper()));
        return kafkaProducerFactory;
    }

    @Bean
    public KafkaTemplate<String, PostClients> pushKafkaTemplate(ProducerFactory<String, PostClients> pushKafkaProducerFactory) {
        return new KafkaTemplate<>(pushKafkaProducerFactory);
    }
}
