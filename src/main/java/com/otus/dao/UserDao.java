package com.otus.dao;

import com.otus.dao.mapper.ClientFriendsPostMapper;
import com.otus.dao.mapper.ClientProfileMapper;
import com.otus.dao.model.Client;
import com.otus.dao.model.ClientProfile;
import com.otus.dao.model.FriendsPost;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.otus.config.CachingConfig.POST;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ClientProfileMapper clientProfileMapper;
    private final ClientFriendsPostMapper clientFriendsPostMapper;

    private static final String SELECT_CLIENT_BY_NAME = "SELECT * "
            + "\nFROM " + "client"
            + " WHERE " + "username" + " = ? LIMIT 1;";

    private static final String SELECT_CLIENT_PROFILE_BY_ID = "SELECT * "
            + "\nFROM " + "client_profile"
            + " WHERE " + "client_id" + " = ? LIMIT 1;";

    private static final String SELECT_CLIENT_PROFILE_BY_LAST_NAME = "SELECT * "
            + "\nFROM " + "client_profile"
            + " WHERE " + "last_name" + " = ? ";

    private static final String SELECT_CLIENT_PROFILE_BY_LAST_NAME_LIKE = "SELECT * "
            + "\nFROM " + "client_profile"
            + " WHERE " + "first_name like " + " ?" + " and last_name like " + " ? order by id";


    private static final String SELECT_POST_CLIENT_FRIEND = "SELECT first_name, last_name, post "
            + "\nFROM " + "client_profile as prof join client_post as pos"
            + " WHERE " + "prof.id = ?  and prof.id = pos.client_id";

    private static final String SELECT_CLIENT_FRIENDS = "SELECT id_friends "
            + "\nFROM " + "client_friends"
            + " WHERE " + "client_profile_id" + " = ? ";

    private static final String INSERT_CLIENT =
            "insert into client ( username, password) values(?,?);";

    private static final String INSERT_CLIENT_PROFILE =
            "insert into client_profile ( first_name, last_name, age, gender, city, client_id, hobby)" +
                    " values(?,?,?,?,?,?,?);";

    private static final String INSERT_FRIENDS =
            "insert into client_friends ( client_profile_id, id_friends) values(?,?);";

    private static final String INSERT_POST =
            "insert into client_post ( client_id, post, create_date) values(?,?, ?);";

    public Client loadClientByUsername(String username) throws UsernameNotFoundException {
        List<Client> clients = jdbcTemplate
                .query(SELECT_CLIENT_BY_NAME, new BeanPropertyRowMapper<>(Client.class), username);
        return DataAccessUtils.uniqueResult(clients);
    }

    public void insertClient(Client client) {
        jdbcTemplate.update(INSERT_CLIENT, client.getUsername(), client.getPassword());
    }

    public void insertClientProfile(ClientProfile client, int id) {
        jdbcTemplate.update(INSERT_CLIENT_PROFILE, client.getFirstName(),
                client.getLastName(), client.getAge(), client.getGender().toString(), client.getCity(), id, client.getHobby());
    }

    @CacheEvict(cacheNames = POST, key = "#id")
    public void insertFriends(int id, String idFriends) {
        try {
            jdbcTemplate.update(INSERT_FRIENDS, id, Integer.valueOf(idFriends.replaceAll("\\u00A0{1,}", "")));
        } catch (Exception e) {
            log.error("дубль ", e);
        }
    }

    public void insertPost(int id, String post, String date) {
        try {
            jdbcTemplate.update(INSERT_POST, id, post, date);
        } catch (Exception e) {
            log.error("insertPost ошибка", e);
        }
    }

    public ClientProfile loadProfileById(int clientId) {
        List<ClientProfile> clients = jdbcTemplate
                .query(SELECT_CLIENT_PROFILE_BY_ID, clientProfileMapper, clientId);
        return DataAccessUtils.uniqueResult(clients);
    }

    public List<ClientProfile> findByLastName(String firstName, String lastName) {
        List<ClientProfile> clients = jdbcTemplate
                .query(SELECT_CLIENT_PROFILE_BY_LAST_NAME_LIKE, clientProfileMapper, firstName + "%", lastName + "%");
        return clients;
    }

    @Cacheable(cacheNames = POST)
    public List<FriendsPost> getPostFriends(int id) {
        System.out.println("getPostFriends");
        List<Integer> clientsId = jdbcTemplate
                .query(SELECT_CLIENT_FRIENDS, (resultSet, i) -> resultSet.getInt("id_friends"), id);

        if (clientsId.isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, Object> params = Collections.singletonMap("fields", clientsId);

        List<FriendsPost> posts = namedParameterJdbcTemplate
                .query("SELECT first_name, last_name, post" +
                        " FROM  client_profile as prof join client_post as pos " +
                        "WHERE  prof.id in (:fields) and prof.id = pos.client_id order by pos.create_date desc limit 1000;", params, clientFriendsPostMapper);
        return posts;
    }

    public List<ClientProfile> findFriendUser(int id) {
        List<Integer> clientsId = jdbcTemplate
                .query(SELECT_CLIENT_FRIENDS, (resultSet, i) -> resultSet.getInt("id_friends"), id);

        if (clientsId.isEmpty()) {
            return Collections.emptyList();
        }
        Map<String, Object> params = Collections.singletonMap("fields", clientsId);
        List<ClientProfile> clients = namedParameterJdbcTemplate
                .query("SELECT * FROM client_profile WHERE id IN (:fields);", params, clientProfileMapper);
        return clients;
    }

    public List<Integer> findClientForAddPost(int id) {

        return jdbcTemplate
                .queryForList("SELECT distinct client_profile_id FROM client_friends WHERE id_friends = ?;", Integer.class, id);
    }

    public List<String> findPostUser(int id) {

        List<String> clients = jdbcTemplate
                .queryForList("SELECT post FROM client_post WHERE client_id = ? order by create_date desc limit 15;", String.class, id);
        return clients;
    }
}
