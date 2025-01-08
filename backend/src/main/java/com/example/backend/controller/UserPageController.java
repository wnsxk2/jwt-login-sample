package com.example.backend.controller;

import com.example.backend.service.UserService;
import com.example.backend.vo.UserVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequiredArgsConstructor
public class UserPageController {

    private final UserService userService;

    @RequestMapping(path = "/signin", method = RequestMethod.GET)
    public String signin() {
        return "signin";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.POST)
    public String userJoin(@ModelAttribute UserVO user) {
        try{
            // 빈 값 검증
            if(user == null || user.getPassword() == null){
                throw new Exception();
            }

            userService.create(user);
            // 회원가입 성공 화면
            return "index";
        } catch (Exception e) {
            // 회원가입 실패 화면
            return  "index";
        }

    }
}
