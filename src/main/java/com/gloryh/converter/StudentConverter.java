package com.gloryh.converter;

import com.gloryh.entity.Student;
import org.springframework.core.convert.converter.Converter;

/**
 * 自定义学生实体类数据转换器
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
public class StudentConverter implements Converter<String, Student> {

    @Override
    public Student convert(String s) {
        //拆分String字符串。例如：38-黄光辉-20
        String[] args=s.split("-");
        Student student=new Student();
        student.setId(Long.parseLong(args[0]));
        student.setName(args[1]);
        student.setAge(Integer.parseInt(args[2]));
        return student;
    }
}
