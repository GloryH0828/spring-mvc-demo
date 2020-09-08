package com.gloryh.entity;

import lombok.Data;

import java.util.Map;

/**
 * 对 Map 进行包装的实体类
 *
 * @author 黄光辉
 * @since 2020/9/1
 **/
@Data
public class UserMap {
    private Map<String ,User> users;
}
