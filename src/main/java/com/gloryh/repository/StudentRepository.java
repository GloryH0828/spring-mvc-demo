package com.gloryh.repository;

import com.gloryh.entity.Student;

import java.util.Collection;

/**
 * REST 相关业务方法的接口类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
public interface StudentRepository {
    /**
     * 查询所有信息
     * @return Collection<Student>
     */
    Collection<Student> findAll();

    /**
     * 按照id查询
     * @param id
     * @return Student
     */
    Student findById(long id);

    /**
     * 更新或者添加
     * @param student
     */
    void saveOrUpdate(Student student);

    /**
     * 按id删除
     * @param id
     */
    void deleteById(long id);
}
