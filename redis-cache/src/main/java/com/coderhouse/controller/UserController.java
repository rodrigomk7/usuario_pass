package com.coderhouse.controller;

import com.coderhouse.model.document.User;
import com.coderhouse.model.request.UserRequest;
import com.coderhouse.model.response.UserResponse;
import com.coderhouse.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/coder-house")
@RequiredArgsConstructor
public class UserController {

    private final UserService service;

    @PostMapping("user/login")
    public UserResponse login(@RequestBody UserRequest request) throws Exception {
        return service.getUser(request);
    }

    @PostMapping("user/register")
    public UserResponse register(@RequestBody UserRequest request) throws Exception {
        return service.register(request);
    }

}
