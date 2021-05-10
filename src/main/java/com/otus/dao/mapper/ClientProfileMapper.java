package com.otus.dao.mapper;

import com.otus.dao.model.ClientProfile;
import com.otus.dao.model.Gender;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClientProfileMapper implements RowMapper<ClientProfile> {

    @Override
    public ClientProfile mapRow(ResultSet rs, int rowNum) throws SQLException {
        ClientProfile clientProfile
                = new ClientProfile();
        clientProfile.setId(rs.getInt("id"));
        clientProfile.setFirstName(rs.getString("first_name"));
        clientProfile.setLastName(rs.getString("last_name"));
        clientProfile.setAge(rs.getInt("age"));
        clientProfile.setGender(Gender.valueOf(rs.getString("gender")));
        clientProfile.setCity(rs.getString("city"));
        clientProfile.setClientId(rs.getInt("client_id"));
        clientProfile.setHobby(rs.getString("hobby"));
        return clientProfile;
    }
}
