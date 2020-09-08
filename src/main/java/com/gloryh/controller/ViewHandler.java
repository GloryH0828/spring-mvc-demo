package com.gloryh.controller;

import com.gloryh.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.view.InternalResourceView;


import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

/**
 * 模型数据解析业务方法
 *
 * @author 黄光辉
 * @since 2020/9/1
 **/
@Controller
@RequestMapping("/view")
public class ViewHandler {

    @RequestMapping("/map")
    public String map(Map<String, User> map) {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        map.put("user", user);
        return "view";
    }

    @RequestMapping("/model")
    public String model(Model model) {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        model.addAttribute("user", user);
        return "view";
    }

    @RequestMapping("/modelAndView")
    public ModelAndView modelAndView() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        user.setId(1);
        user.setName("张三");
        modelAndView.addObject("user", user);
        modelAndView.setViewName("view");
        return modelAndView;
    }

    @RequestMapping("/modelAndView2")
    public ModelAndView modelAndView2() {
        ModelAndView modelAndView = new ModelAndView();
        User user = new User();
        user.setId(1);
        user.setName("张三");
        modelAndView.addObject("user", user);
        View view = new InternalResourceView("/view.jsp");
        modelAndView.setView(view);
        return modelAndView;
    }

    @RequestMapping("/modelAndView3")
    public ModelAndView modelAndView3() {
        ModelAndView modelAndView = new ModelAndView("view");
        User user = new User();
        user.setId(1);
        user.setName("张三");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping("/modelAndView4")
    public ModelAndView modelAndView4() {
        View view = new InternalResourceView("/view.jsp");
        ModelAndView modelAndView = new ModelAndView(view);
        User user = new User();
        user.setId(1);
        user.setName("张三");
        modelAndView.addObject("user", user);
        return modelAndView;
    }

    @RequestMapping("/modelAndView5")
    public ModelAndView modelAndView5() {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        Map<String, User> map = new HashMap<>();
        map.put("user", user);
        ModelAndView modelAndView = new ModelAndView("view", map);
        return modelAndView;
    }

    @RequestMapping("/modelAndView6")
    public ModelAndView modelAndView6() {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        ModelAndView modelAndView = new ModelAndView("view", "user", user);
        return modelAndView;
    }

    @RequestMapping("/request")
    public String request(HttpServletRequest request) {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        request.setAttribute("user", user);
        return "view";
    }

    @ModelAttribute
    public void getUser(Model model) {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        model.addAttribute("user", user);
    }

    @RequestMapping("/modelAttribute")
    public String modelAttribute() {
        return "view";
    }

    @RequestMapping("/session")
    public String session(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = new User();
        user.setId(1);
        user.setName("张三");
        session.setAttribute("user", user);
        return "view";
    }

    @RequestMapping("/session2")
    public String session2(HttpSession session) {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        session.setAttribute("user", user);
        return "view";
    }

    @RequestMapping("/application")
    public String application(HttpServletRequest request) {
        User user = new User();
        user.setId(1);
        user.setName("张三");
        ServletContext application=request.getServletContext();
        application.setAttribute("user",user);
        return "view";
    }
}
