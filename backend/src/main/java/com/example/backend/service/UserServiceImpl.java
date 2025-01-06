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
    public void addUser(UserVO createUser) {
        return userMapper.insertUser(createUser);
    }
}
