package com.szxy.service.impl;

import com.szxy.dao.UserMapper;
import com.szxy.pojo.User;
import com.szxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Cacheable(cacheNames = {"user"})
    public List<User> getAllUser() {
        System.out.println("UserServiceImpl:getAllUser()" );
        List<User> users = userMapper.selAll();
        return users;
    }

    @Override
    public List<Map> getAllMap() {
        List<Map> maps = userMapper.seleAllMap();
        return maps;
    }

}
