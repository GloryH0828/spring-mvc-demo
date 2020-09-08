package com.gloryh.entity;

import lombok.Data;

/**
 * 用户实体类，用于测试JavaBean数据绑定
 *
 * @author 黄光辉
 * @since 2020/8/31
 **/
@Data
public class User {
    private int id;
    private String name;
    private Address address;
}
