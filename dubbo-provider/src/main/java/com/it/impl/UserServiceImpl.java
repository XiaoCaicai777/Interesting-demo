package com.it.impl;

import com.it.model.User;
import com.it.service.UserService;
import org.apache.dubbo.config.annotation.Method;
import org.apache.dubbo.config.annotation.Service;

@Service(retries = 0,version = "3")
public class UserServiceImpl implements UserService {
    @Override
    public User addUser(String name, String world) {
        System.out.println("name3 : " + name + "world3 : " + world);
       if(Math.random()>0.5){
           throw new RuntimeException();
       }
        return new User(name,world);
    }
}
