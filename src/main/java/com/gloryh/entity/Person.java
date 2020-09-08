package com.gloryh.entity;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * 用于 Annotation JSR -303 的数据校验实体类
 *
 * @author 黄光辉
 * @since 2020/9/8
 **/
@Data
public class Person {
    @NotEmpty(message = "用户名不能为空")
    private String name;

    @Size(min = 6,max =12,message = "密码长度应该为6-12位")
    private String password;
    @Email(regexp = "^[A-Za-z\\d]+([-_.][A-Za-z\\d]+)*@([A-Za-z\\d]+[-.])+[A-Za-z\\d]{2,4}$",message = "请输入正确的邮箱格式")
    private String email;
    @Pattern(regexp = "/^(13[0-9]|14[0-9]|15[0-9]|166|17[0-9]|18[0-9]|19[8|9])\\d{8}$/",message = "请输入正确的手机号")
    private String phone;
}
