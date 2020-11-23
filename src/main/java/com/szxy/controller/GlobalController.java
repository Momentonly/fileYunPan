package com.szxy.controller;

import com.szxy.config.helloservlet.HelloServlet;
import com.szxy.util.AccessIPUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;

@Controller
@RequestMapping("/gla")
public class GlobalController {

    @RequestMapping("/add")
    public String toAdd(){
        return "//////add";
    }

    @RequestMapping("get")
    public String toGet(){
        return "get";
    }

    /**
     * 动态注册servlet,异常
     *         java.lang.IllegalStateException:
     *         Servlets cannot be added to context [] as the context has been initialised
     *         servlet不能添加到上下文[]，因为上下文已被初始化
     * @param request
     * @return
     */
    @RequestMapping("/regServlet")
    public String regServlet(HttpServletRequest request){
        ServletContext sc = request.getServletContext();
        ServletRegistration.Dynamic helloServlet = sc.addServlet("helloServlet", new HelloServlet());
        helloServlet.addMapping("/hello");
        return "index";
    }

    /**
     * 获取客户端ip地址
     * @param request
     * @return
     */
    @RequestMapping("/getIp")
    @ResponseBody
    public Object getIP(HttpServletRequest request){
        String ipAddress = AccessIPUtil.getIpAddress(request);
        String ipAddr = AccessIPUtil.getIpAddr(request);
        HashMap<String, Object> map = new HashMap<String, Object>();
        map.put("ip1", ipAddress);
        map.put("ip2", ipAddr);
        return map;
    }

}
