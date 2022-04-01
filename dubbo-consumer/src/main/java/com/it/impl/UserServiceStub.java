package com.it.impl;

import com.it.model.User;
import com.it.service.UserService;
import org.springframework.util.StringUtils;


/**
 * 本地存根一般都放在公共接口里
 */
public class UserServiceStub implements UserService {
    private final UserService userService;


    public UserServiceStub(UserService userService){
        this.userService = userService;
    }

    @Override
    public User addUser(String name, String world) {
        System.out.println("本地存根...");
        if(StringUtils.isEmpty(name) || StringUtils.isEmpty(world)){
            return null;
        }
        return userService.addUser(name,world);
    }
}
