package com.example.backend.service;

import com.example.backend.vo.UserVO;

public interface UserService {
    public UserVO getAllUsers();

    public void addUser(UserVO createUser);
}
