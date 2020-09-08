package com.gloryh.controller;

import com.gloryh.entity.Student;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 表单标签库业务方法测试
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@Controller
@RequestMapping("/tag")
public class TagHandler {

    @GetMapping("/get")
    public ModelAndView get(){
        ModelAndView modelAndView=new ModelAndView("tag");
        Student student =new Student();
        student.setId(1L);
        student.setName("张三");
        student.setAge(20);
        student.isMale();
        student.setHobby(Arrays.asList("吃","睡","玩","乐","学"));
        student.setSelectHobbies(Arrays.asList("吃","睡","玩"));
        student.setRadioId(2);
        student.setCity("郑州");
        student.setIntroduce("活泼开朗，学习认真");
        Map<Integer,String> grade=new HashMap<>();
        grade.put(1,"一年级");
        grade.put(2,"二年级");
        grade.put(3,"三年级");
        modelAndView.addObject("grade",grade);
        String[] city={"洛阳","开封","郑州"};
        modelAndView.addObject("city",city);
        modelAndView.addObject("student",student);
        return modelAndView;
    }
}
