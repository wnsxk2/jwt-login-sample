package com.example.backend.controller;

import com.example.backend.service.UserService;
import com.example.backend.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping(path = "/test", method = RequestMethod.GET)
    public ResponseEntity<?> getAllUsers() {
        UserVO user = userService.getAllUsers();
        return ResponseEntity.ok().body(user);
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public ResponseEntity<?> registerUser(@RequestBody UserVO user) {
        try{
            if(user == null || user.getPassword() == null){
                throw new Exception();
            }
            UserVO createUser = new UserVO();
            createUser.setUsername(user.getUsername());
            createUser.setPassword(user.getPassword());

            userService.addUser(createUser);
        } catch (Exception e) {

        }
        return
    }
}
