package com.otus.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.otus.kafka.model.PostClients;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.common.serialization.Deserializer;

@Slf4j
public class PostClientsDeserializer implements Deserializer<PostClients> {

    private static final ObjectMapper mapper = new ObjectMapper();

    @Override
    public PostClients deserialize(String topic, byte[] data) {
        PostClients queuePushMessage = null;
        try {
            queuePushMessage = mapper.readValue(data, PostClients.class);
        } catch (Exception e) {
            log.error("Ошибка десериализация из топика кафки ", e);
        }

        return queuePushMessage;
    }
}
