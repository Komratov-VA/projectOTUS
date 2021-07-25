package com.otus.service;

import com.otus.dao.UserDao;
import com.otus.dao.model.Client;
import com.otus.kafka.KafkaProducer;
import com.otus.kafka.model.PostClients;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class PostService {

    private final UserDao userDao;
    private final KafkaProducer kafkaProducer;

    @Transactional
    public void addPost(Client client, String postClient) {
        String datePost = LocalDateTime.now().toString();
        userDao.insertPost(client.getProfileId(), postClient, datePost);
        kafkaProducer.sendPost(PostClients.builder().
                id(client.getProfileId()).post(postClient).firstName(client.getFirstName()).
                lastName(client.getLastName()).date(datePost).build());
    }
}
