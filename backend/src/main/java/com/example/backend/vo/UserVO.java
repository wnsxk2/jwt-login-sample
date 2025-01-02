package com.example.backend.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserVO {
    private int id;
    private String email;
    private String passwordHash;
    private Timestamp createdAt;
    private Timestamp updatedAt;

}
