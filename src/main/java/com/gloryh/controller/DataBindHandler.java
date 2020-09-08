package com.gloryh.controller;

import com.gloryh.entity.User;
import com.gloryh.entity.UserList;
import com.gloryh.entity.UserMap;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

/**
 * 数据绑定业务方法类
 *
 * @author 黄光辉
 * @since 2020/8/31
 **/
@RestController
@RequestMapping("/data")
public class DataBindHandler {

    @RequestMapping("/baseType")
    @ResponseBody
    public String baseType(int id) {
        return id + "";
    }

    @RequestMapping("/packageType")
    @ResponseBody
    public String packageType(@RequestParam(value = "num", required = false, defaultValue = "0") Integer id) {
        return id + "";
    }

    @RequestMapping("/array")
    @ResponseBody
    public String array(Integer[] num) {
        String str = Arrays.toString(num);
        return str;
    }

    @RequestMapping("/list")
    @ResponseBody
    public String list(UserList userList) {
        String str = "";
        for (User user : userList.getUsers()) {
            str += user;
        }
        return str;
    }

    @RequestMapping("/map")
    @ResponseBody
    public String map(UserMap userMap) {
        String str = "";
        for (String key : userMap.getUsers().keySet()) {
            User user = userMap.getUsers().get(key);
            str += user;
        }
        return str;
    }

    @RequestMapping("/json")
    @ResponseBody
    public User json(@RequestBody User user) {
        System.out.println(user);
        user.setId(2);
        user.setName("李四");
        return user;
    }

}
