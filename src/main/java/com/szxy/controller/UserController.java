package com.szxy.controller;

import com.szxy.pojo.User;
import com.szxy.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("/selUser")
    public String selUser(Model model){
        System.out.println("缓存管理器类型:" + cacheManager.getClass().getSimpleName());
        System.out.println("缓存管理器包含缓存名称:" + cacheManager.getCacheNames());
        List<User> allUser = userService.getAllUser();
        System.out.println("all Users:");
        for (User user : allUser) {
            System.out.println(user);
        }
        model.addAttribute("users", allUser);
        return "showUser";
    }

    @RequestMapping("/selMap")
    public String selMap(Model model){
        List<Map> allMap = userService.getAllMap();
        model.addAttribute("users", allMap);
        return "showUser";
    }

}