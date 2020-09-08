package com.gloryh.controller;

import com.gloryh.entity.Student;
import com.gloryh.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * REST 相关操作业务实现类
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@RestController
@RequestMapping("/rest")
public class RestHandler {
    @Autowired
    private StudentRepository studentRepository;

    /**
     * 查询操作使用GET请求 :@RequestMapping(value = "/findAll",produces = "text/json;charset=UTF-8",method = RequestMethod.GET)
     *      * 同时可以简写为：@GetMapping(value = "/findAll",produces ="text/json;charset=UTF-8" )
     * 实现查询所有Student ,使用 produces 处理中文乱码
     * @return Collection<Student>
     */
    @GetMapping(value = "/findAll",produces ="text/json;charset=UTF-8" )
    public Collection<Student> findAll(){
        return studentRepository.findAll();
    }

    /**
     * 按id查询
     * @param id
     * @return
     */
    @GetMapping(value = "/find/{id}",produces ="text/json;charset=UTF-8")
    public Student findById(@PathVariable("id") long id){
        return studentRepository.findById(id);
    }

    /**
     * 添加
     * @param student
     */
    @PostMapping(value = "/save")
    public void save(@RequestBody Student student){
        studentRepository.saveOrUpdate(student);
    }

    /**
     * 更新
     * @param student
     */
    @PutMapping("/update")
    public void update(@RequestBody Student student){
        studentRepository.saveOrUpdate(student);
    }

    /**
     * 按id删除
     * @param id
     */
    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable("id") long id){
        studentRepository.deleteById(id);
    }
}
