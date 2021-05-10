package com.otus.dao;

import com.otus.dao.mapper.ClientProfileMapper;
import com.otus.dao.model.Client;
import com.otus.dao.model.ClientProfile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserDao {

    private final JdbcTemplate jdbcTemplate;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    private final ClientProfileMapper clientProfileMapper;

    private static final String SELECT_CLIENT_BY_NAME = "SELECT * "
            + "\nFROM " + "client"
            + " WHERE " + "username" + " = ? LIMIT 1;";

    private static final String SELECT_CLIENT_PROFILE_BY_ID = "SELECT * "
            + "\nFROM " + "client_profile"
            + " WHERE " + "client_id" + " = ? LIMIT 1;";

    private static final String SELECT_CLIENT_PROFILE_BY_LAST_NAME = "SELECT * "
            + "\nFROM " + "client_profile"
            + " WHERE " + "last_name" + " = ? ";

    private static final String SELECT_CLIENT_FRIENDS = "SELECT id_friends "
            + "\nFROM " + "client_friends"
            + " WHERE " + "client_profile_id" + " = ? ";

    private static final String INSERT_CLIENT =
            "insert into client ( username, password) values(?,?);";

    private static final String INSERT_FRIENDS =
            "insert into client_friends ( client_profile_id, id_friends) values(?,?);";


    @PostConstruct
    public void ee() throws SQLException {
        System.out.println(jdbcTemplate.getDataSource().getConnection().getClientInfo());
        System.out.println(jdbcTemplate.query("Select * from client;",
                (resultSet, i) -> resultSet.getInt("id")));

        System.out.println(loadClientByUsername("seva"));
        System.out.println(loadClientByUsername("seva2"));
        System.out.println(loadProfileById(1));
    }

    public Client loadClientByUsername(String username) throws UsernameNotFoundException {
        System.out.println("load");
        List<Client> clients = jdbcTemplate
                .query(SELECT_CLIENT_BY_NAME, new BeanPropertyRowMapper<>(Client.class), username);
        return DataAccessUtils.uniqueResult(clients);
    }

    public void insertClient(Client client) {
        jdbcTemplate.update(INSERT_CLIENT, client.getUsername(), client.getPassword());
    }

    public void insertFriends(int id, String idFriends) {
        try {
            jdbcTemplate.update(INSERT_FRIENDS, id, idFriends);
        } catch (Exception e) {
            System.out.println("дубль");
        }
    }

    public ClientProfile loadProfileById(int clientId) {
        List<ClientProfile> clients = jdbcTemplate
                .query(SELECT_CLIENT_PROFILE_BY_ID, clientProfileMapper, clientId);
        return DataAccessUtils.uniqueResult(clients);
    }

    public List<ClientProfile> findByLastName(String lastName) {
        List<ClientProfile> clients = jdbcTemplate
                .query(SELECT_CLIENT_PROFILE_BY_LAST_NAME, clientProfileMapper, lastName);
        return clients;
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
}