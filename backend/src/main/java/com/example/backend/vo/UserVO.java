package com.example.backend.vo;

import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class UserVO {
    private String token;
    private String username;
    private String password;
    
    private String id;

}
