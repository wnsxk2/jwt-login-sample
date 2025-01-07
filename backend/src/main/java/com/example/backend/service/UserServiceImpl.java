package com.example.backend.service;

import com.example.backend.mapper.UserMapper;
import com.example.backend.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;

    @Override
    public UserVO getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public void create(UserVO user) {
        userMapper.insertUser(user);
    }

    @Override
    public UserVO getByCredentials(UserVO user) {
        return userMapper.findByUsernameAndPassword(user);
    }

    @Override
    public void saveToken(UserVO user) {
        userMapper.saveRefreshToken(user);
    }

    @Override
    public String getToken(UserVO user) {
        return userMapper.getRefreshToken(user);
    }
}
