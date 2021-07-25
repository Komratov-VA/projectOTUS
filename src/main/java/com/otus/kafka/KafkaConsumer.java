package com.otus.kafka;

import com.otus.kafka.model.PostClients;
import com.otus.service.HandlerKafkaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.List;


@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaConsumer {

    private final HandlerKafkaService handlerKafkaService;

    @KafkaListener(topics = "${spring.kafka.consumer.topic}")
    public void listen(@Payload List<PostClients> message) {
        handlerKafkaService.process(message);
    }
}
