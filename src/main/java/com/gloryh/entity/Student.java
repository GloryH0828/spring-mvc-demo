package com.gloryh.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * 自定义学生类型实体类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private long id;
    private String name;
    private int age;
    private boolean male;
    private List<String> hobby;
    private List<String> selectHobbies;
    private int radioId;
    private String city;
    private String introduce;
}
