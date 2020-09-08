package com.gloryh.entity;

import lombok.Data;

/**
 * 用于 Validator 数据校验的实体类
 *
 * @author 黄光辉
 * @since 2020/9/8
 **/
@Data
public class Account {
    private String name;
    private String password;
}
