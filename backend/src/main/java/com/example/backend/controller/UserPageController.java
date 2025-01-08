package com.example.backend.controller;

import com.example.backend.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserPageController {

    private final UserService userService;

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index(Authentication authentication) {
        return "index";
    }

    @RequestMapping(path = "/signin", method = RequestMethod.GET)
    public String signin() {
        return "signin";
    }

    @RequestMapping(path = "/signup", method = RequestMethod.GET)
    public String signup() {
        return "signup";
    }

    @RequestMapping(path = "/success", method = RequestMethod.GET)
    public String success(Authentication authentication) {
        log.debug("Authentication.getName: {}", authentication.getName());
        log.debug("Authentication.isAuthenticated: {}", authentication.isAuthenticated());
        return "success";
    }
}
