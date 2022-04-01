package com.it.controller;

import com.it.model.User;
import com.it.service.UserService;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController {
    @Reference
    private UserService userService;

    @GetMapping("/addUser")
    @HystrixCommand(fallbackMethod = "fallback")
    @ResponseBody
    public User addUser(String name, String world){

        return userService.addUser(name, world);
    }

    public User fallback(String name, String world){
        return new User("fallback","fallback");
    }
}
