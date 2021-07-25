package com.otus.service;

import com.otus.dao.UserDao;
import com.otus.dao.model.FriendsPost;
import com.otus.kafka.model.PostClients;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

import static com.otus.config.CachingConfig.POST;

@Component
@RequiredArgsConstructor
@Slf4j
public class HandlerKafkaService {

    private final CacheManager cacheManager;
    private final UserDao userDao;

    public void process(List<PostClients> message) {
        System.out.println(message);
        com.github.benmanes.caffeine.cache.Cache<Object, Object> cache = (com.github.benmanes.caffeine.cache.Cache<Object, Object>) cacheManager.getCache(POST).getNativeCache();
        Map list = cache.asMap();
        message.forEach(x -> addCache(x, list));
    }

    private void addCache(PostClients postClients, Map list) {
        String firstName = postClients.getFirstName();
        String lastName = postClients.getLastName();
        String post = postClients.getPost();
        List<Integer> listClientId = userDao.findClientForAddPost(postClients.getId());
        listClientId.stream().filter(x -> list.containsKey(x)).forEach(x -> {
            List<FriendsPost> postList = (List<FriendsPost>) list.get(x);
            log.info("Добвление в кэш поста: " + postClients);
            if(postList.size() == 1000) {
                postList.remove(1000);
            }
            postList.add(0, new FriendsPost(firstName, lastName, post));
        });
    }
}
