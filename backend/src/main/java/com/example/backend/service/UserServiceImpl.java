package com.example.backend.service;

import com.example.backend.mapper.UserMapper;
import com.example.backend.security.TokenProvider;
import com.example.backend.vo.UserVO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final BCryptPasswordEncoder encoder;

    @Override
    public UserVO getAllUsers() {
        return userMapper.selectAllUsers();
    }

    @Override
    public void create(UserVO user) {
        // DB 저장 비밀번호 암호화
        user.setPassword(encoder.encode(user.getPassword()));
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

    @Override
    public String signIn(UserVO user) {
        String username = user.getUsername();
        String password = user.getPassword();
        UserVO member = userMapper.findByUsername(username);

        // email 검사
        if(member != null) {
            log.debug("user가 존재하지 않습니다.");
        }

        // password 검사
        if(!encoder.matches(password, member.getPassword())) {
            log.debug("비밀번호가 일치하지 않습니다.");
        }

        // token 생성
        String accessToken = TokenProvider.generateToken(member);

        return accessToken;
    }
}
