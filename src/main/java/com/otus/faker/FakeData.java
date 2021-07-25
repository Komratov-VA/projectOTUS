package com.otus.faker;

import com.github.javafaker.Faker;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.*;

@Component
@RequiredArgsConstructor
@ConditionalOnProperty(prefix = "spring", name = "goFake")
public class FakeData {

    private static final String INSERT_CLIENT_PROFILE =
            "insert into client_profile ( first_name, last_name, age, gender, city, client_id)" +
                    " values(?,?,?,?,?,?);";

    private static final String INSERT_CLIENT =
            "insert into client (username, password)" +
                    " values(?,?);";

    private final JdbcTemplate jdbcTemplate;
    private final PasswordEncoder passwordEncoder;


    @PostConstruct
    public void goFake() throws InterruptedException {
        System.out.println("началась генерация");
        deleteData();
        List<Integer> list = new ArrayList<>();
        //2000 по 500
        Faker faker = new Faker(new Locale("ru"));
        for (int i = 1; i < 1000000; i++) {
            insertClientProfileFake(faker, i, list);
        }
    }

    public void deleteData() {
        try {
            jdbcTemplate.execute("delete from client_friends");
            jdbcTemplate.execute("delete from client_profile");
            jdbcTemplate.execute("delete from client_profile_fake");
            jdbcTemplate.execute("delete from client");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void insertClientProfileFake(Faker faker, int id1, List<Integer> list) {
//        int age = 12 + (int) (Math.random() * 50);
//        String gender = age % 2 == 0 ? "m" : "w";
//        String user = faker.name().username();
//        String user = String.valueOf(id);
//        String password = passwordEncoder.encode(String.valueOf(id));
//        if (!set.contains(user)){
        list.add(id1);
        if(id1 % 500 == 0) {
//            jdbcTemplate.update(INSERT_CLIENT, user, password);
            List<Integer> finalList = list;
            jdbcTemplate.batchUpdate(INSERT_CLIENT, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    String id  = finalList.get(i).toString();
                    System.out.println(id);
                    System.out.println(finalList);
//                    ps.setString(1, id);
                    ps.setString(1, id);
                    ps.setString(2, passwordEncoder.encode(id));
                }

                @Override
                public int getBatchSize() {
                    return 500;
                }
            });
//        List<Integer> i1 = jdbcTemplate.query("select id from client where username = ?", (resultSet, i) -> resultSet.getInt("id"), user);
//            jdbcTemplate.update(INSERT_CLIENT_PROFILE, faker.name().firstName(),
//                    faker.name().lastName(), age, gender, faker.address().city(), );
            List<Integer> finalList1 = list;
            jdbcTemplate.batchUpdate(INSERT_CLIENT_PROFILE, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    String id  = finalList1.get(i).toString();
                    int age = 12 + (int) (Math.random() * 50);
                    String gender = age % 2 == 0 ? "m" : "w";
//                    ps.setString(1, id);
                    ps.setString(1,faker.name().firstName());
                    ps.setString(2, faker.name().lastName());
                    ps.setInt(3,age);
                    ps.setString(4,gender);
                    ps.setString(5,faker.address().city());
                    ps.setInt(6, finalList1.get(i));
//                    ps.setString(8,id);
                }

                @Override
                public int getBatchSize() {
                    return 500;
                }
            });
            System.out.println("here");
            list.clear();
        }

//        set.add(user);
//        }
    }

    //return jdbcTemplate.batchUpdate("INSERT INTO EMPLOYEE VALUES (?, ?, ?, ?)",
    //        new BatchPreparedStatementSetter() {
    //            @Override
    //            public void setValues(PreparedStatement ps, int i) throws SQLException {
    //                ps.setInt(1, employees.get(i).getId());
    //                ps.setString(2, employees.get(i).getFirstName());
    //                ps.setString(3, employees.get(i).getLastName());
    //                ps.setString(4, employees.get(i).getAddress();
    //            }
    //            @Override
    //            public int getBatchSize() {
    //                return 50;
    //            }
    //        });
}
