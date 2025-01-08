package com.example.backend.service;

import com.example.backend.mapper.UserMapper;
import com.example.backend.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB 검사가 아니라
        UserVO member = userMapper.findByUsername(username);
        if (member == null) {
            throw new UsernameNotFoundException("없는 회원 입니다...");
        }

        return User.builder().username(member.getUsername()).password(member.getPassword()).build();
    }
}
