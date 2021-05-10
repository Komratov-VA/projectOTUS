package com.otus.dao.model;

import lombok.Data;

@Data
public class ClientProfile {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private Gender gender;
    private String city;
    private int clientId;
    private String hobby;
}
