package com.gloryh.validator;

import com.gloryh.entity.Account;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * 自定义用于Account实体类的数据校验 验证器
 *
 * @author 黄光辉
 * @since 2020/9/8
 **/
public class AccountValidator implements Validator {
    @Override
    public boolean supports(Class<?> aClass) {
        //判断是否为 Account 实体类
        return Account.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        //如果是 Account 实体类，首先进行非空验证
        ValidationUtils.rejectIfEmpty(errors,"name",null,"姓名不能为空！");
        ValidationUtils.rejectIfEmpty(errors,"password",null,"密码不能为空！");

    }
}
