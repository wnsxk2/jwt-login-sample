package com.example.backend.service;

import com.example.backend.vo.UserVO;

public interface UserService {
    public UserVO getAllUsers();
    public void create(UserVO user);
    public UserVO getByCredentials(UserVO user);
    public void saveToken(UserVO user);
    public String getToken(UserVO user);
}
