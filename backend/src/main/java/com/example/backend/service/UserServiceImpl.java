package com.example.backend.service;

import com.example.backend.mapper.UserMapper;
import com.example.backend.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserVO getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public void create(UserVO user) {
        // DB 저장 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userMapper.insertUser(user);
    }

    @Override
    public UserVO getByCredentials(UserVO user) {
        return userMapper.findByUsername(user.getUsername());
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
