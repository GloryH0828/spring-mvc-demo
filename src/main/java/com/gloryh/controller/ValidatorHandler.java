package com.gloryh.controller;

import com.gloryh.entity.Account;
import com.gloryh.entity.Person;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * 用于数据校验的业务方法
 *
 * @author 黄光辉
 * @since 2020/9/8
 **/
@Controller
@RequestMapping("/validator")
public class ValidatorHandler {

    @GetMapping("/login")
    public String login(Model model){
        //使用方法初始化 前端中的对应实体类并完成绑定
        model.addAttribute("account",new Account());
        return "login";
    }
    @PostMapping("/login")
    public String login(@Validated Account account, BindingResult bindingResult){
        //使用 @Validated 对调用数据校验器数据进行验证，并返回绑定结果集，结果集中出现错误则返回原网页打印错误
        if(bindingResult.hasErrors()){
            return "login";
        }
        return "index";
    }

    @GetMapping("/register")
    public String register(Model model){
        //使用方法初始化 前端中的对应实体类并完成绑定
        model.addAttribute("person",new Person());
        return "register2";
    }
    @PostMapping("/register")
    public String register(@Valid Person person, BindingResult bindingResult ){
        //使用 @Valid 对调用数据校验器数据进行验证，并返回绑定结果集，结果集中出现错误则返回原网页打印错误
        if (bindingResult.hasErrors()){
            return "register2";
        }
        return "index";
    }
}
