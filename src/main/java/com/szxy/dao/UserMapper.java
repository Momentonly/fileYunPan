package com.szxy.dao;

import com.szxy.pojo.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {
      /**
       * 查询全部用户
       * @return
       */
      List<User> selAll();

      /**
       * 查询全部用户
       *    使用Map集合封装
       * @return
       */
      List<Map> seleAllMap();
}
