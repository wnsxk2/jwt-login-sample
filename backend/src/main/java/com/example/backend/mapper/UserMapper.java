package com.example.backend.mapper;

import com.example.backend.vo.UserVO;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    public UserVO selectAllUsers();
    public void insertUser(UserVO user);
    public UserVO findByUsernameAndPassword(UserVO user);
    public void saveRefreshToken(UserVO user);
    public String getRefreshToken(UserVO user);
}
