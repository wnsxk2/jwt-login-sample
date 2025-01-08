package com.example.backend.controller;

import com.example.backend.security.TokenProvider;
import com.example.backend.service.UserService;
import com.example.backend.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;

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
            userService.create(user);
            return ResponseEntity.ok().body(user);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody UserVO loginUser) {

        String token = userService.signIn(loginUser);
        if(token == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        // token 정보 db 저장 로직

        return ResponseEntity.ok().body(token);
    }
}
