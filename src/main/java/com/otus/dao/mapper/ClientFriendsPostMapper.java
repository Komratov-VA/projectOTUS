package com.otus.dao.mapper;

import com.otus.dao.model.FriendsPost;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class ClientFriendsPostMapper implements RowMapper<FriendsPost> {

    @Override
    public FriendsPost mapRow(ResultSet rs, int rowNum) throws SQLException {
        FriendsPost clientProfile = new FriendsPost();
        clientProfile.setFirstName(rs.getString("first_name"));
        clientProfile.setLastName(rs.getString("last_name"));
        clientProfile.setText(rs.getString("post"));
        return clientProfile;
    }
}
