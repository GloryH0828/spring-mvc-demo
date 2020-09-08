package com.gloryh.entity;

import lombok.Data;

import java.util.List;

/**
 * 对List进行包装的实体类
 *
 * @author 黄光辉
 * @since 2020/9/1
 **/
@Data
public class UserList {
    private List<User> users;
}
