package com.otus.kafka;

import com.otus.kafka.model.PostClients;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;


@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaProducer {

    private final KafkaTemplate<String, PostClients> kafkaTemplate;

    @Value("${spring.kafka.producer.topic}")
    private String topicIn;

    public void sendPost(PostClients post) {
        kafkaTemplate.send(topicIn, post);
    }
}
