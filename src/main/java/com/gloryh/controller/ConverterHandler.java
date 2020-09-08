package com.gloryh.controller;

import com.gloryh.entity.Student;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/**
 * 数据转换器实现类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@RestController
@RequestMapping("/converter")
public class ConverterHandler {

    @RequestMapping("/date")
    public String date(Date date) {
        return date.toString();
    }

    @RequestMapping("/student")
    public String student(Student student) {
        return student.toString();
    }

}
