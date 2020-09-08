package com.gloryh.controller;

import com.gloryh.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * MVC 对应业务层
 *
 * @author 黄光辉
 * @since 2020/8/31
 **/
@Controller
@RequestMapping("/hello")
public class HelloHandler {
    @RequestMapping(value = "/index", method = RequestMethod.GET, params = {"name", "id=10"})
    public String index(@RequestParam("name") String str, @RequestParam("id") int age) {
        System.out.println(str);
        System.out.println(age);
        System.out.println("执行了index");
        return "index";
    }

    @RequestMapping("/rest/{name}/{id}")
    public String rest(@PathVariable("name") String str, @PathVariable("id") int age) {
        System.out.println(str);
        System.out.println(age);
        System.out.println("执行了index");
        return "index";
    }

    /**
     * 利用注解@CookieValue(value = "JSESSIONID"）取出sessionID
     * @param sessionId
     * @return index.jsp
     */
    @RequestMapping("/cookie")
    public String cookie(@CookieValue(value = "JSESSIONID") String sessionId) {
        System.out.println(sessionId);
        return "index";
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    public String save(User user){
        System.out.println(user);
        return "index";
    }

    @RequestMapping("/forward")
    public String forward(){
        return "forward:/index.jsp";
    }

    @RequestMapping("/redirect")
    public String redirect(){
        return "redirect:/index.jsp";
    }
}