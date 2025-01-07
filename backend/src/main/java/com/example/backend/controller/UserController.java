package com.example.backend.controller;

import com.example.backend.security.TokenProvider;
import com.example.backend.service.UserService;
import com.example.backend.vo.UserVO;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final TokenProvider tokenProvider;
    private final AuthenticationManager authenticationManager;

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

            userService.create(createUser);
            return ResponseEntity.ok().body(createUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @RequestMapping(path = "/signin", method = RequestMethod.POST)
    public ResponseEntity<?> loginUser(@RequestBody UserVO loginUser) {
//        try{
//            // signin 입력 값 확인
//            if(user == null || user.getPassword() == null){
//                throw new Exception();
//            }
//
//            // 회원 확인
//            UserVO signinUser = userService.getByCredentials(user);
//
//            if(signinUser != null){
//                // token 생성
//                String token = tokenProvider.generateToken(signinUser);
//                signinUser.setToken(token);
//                return ResponseEntity.ok().body(signinUser);
//            } else {
//                // 회원이 없는 경우
//                return ResponseEntity.badRequest().body("Invalid credentials");
//            }
//        } catch (Exception e) {
//            return ResponseEntity.badRequest().body(e.getMessage());
//        }

        UserVO user = userService.getByCredentials(loginUser);
        if(user == null){
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        // jwt 토큰 생성
        String accessToken = tokenProvider.generateToken(user);
        String refreshToken = tokenProvider.generateToken(user);

        // token 정보 db 저장 로직
        user.setToken(accessToken);

        return ResponseEntity.ok().body(user);
    }
}
