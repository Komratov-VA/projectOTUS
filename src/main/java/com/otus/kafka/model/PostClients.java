package com.otus.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PostClients implements Serializable {

    private int id;
    private String firstName;
    private String lastName;
    private String post;
    private String date;
}
