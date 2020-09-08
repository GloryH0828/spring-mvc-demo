package com.gloryh.repository.impl;

import com.gloryh.entity.Student;
import com.gloryh.repository.StudentRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * REST 相关业务方法的接口的实现类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@Repository
public class StudentRepositoryImpl implements StudentRepository {
    /**
     * 定义一个静态集合模拟数据库
     */
    private static final Map<Long,Student> studentMap;
    static{
        studentMap=new HashMap<>();
        /*studentMap.put(1L, new Student(1L, "张三", 22));
        studentMap.put(2L, new Student(2L, "李四", 23));
        studentMap.put(3L, new Student(3L, "王五", 24));*/
    }
    /**
     * 查询所有信息
     *
     * @return Collection<Student>
     */
    @Override
    public Collection<Student> findAll() {
        return studentMap.values();
    }

    /**
     * 按照id查询
     *
     * @param id
     * @return Student
     */
    @Override
    public Student findById(long id) {
        return studentMap.get(id);
    }

    /**
     * 更新或者添加
     *
     * @param student
     */
    @Override
    public void saveOrUpdate(Student student) {
        studentMap.put(student.getId(),student);
    }

    /**
     * 按id删除
     *
     * @param id
     */
    @Override
    public void deleteById(long id) {
        studentMap.remove(id);
    }
}
