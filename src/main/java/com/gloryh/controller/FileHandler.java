package com.gloryh.controller;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 文件上传下载业务方法
 *
 * @author 黄光辉
 * @since 2020/9/4
 **/
@Controller
@RequestMapping("/file")
public class FileHandler {

    @PostMapping("/upload")
    public String upload(MultipartFile img, HttpServletRequest request){
        //文件不为空，保存文件
        if (img.getSize()>0){
            //获取文件保存的绝对路径file
            String path=request.getServletContext().getRealPath("file");
            //获取上传的文件名
            String name=img.getOriginalFilename();
            //在要保存的路径内创建一个空的文件，文件名为字段 name
            File folder=new File(path);
            if (!folder.exists()){
                folder.mkdir();
            }
            File file=new File(path,name);
            //将 img 的 数据 赋到该空文件中
            try {
                img.transferTo(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            //保存上传之后的文件路径,传回前端进行预览
            request.setAttribute("path","/file/"+name);

        }
        return "upload";
    }
    @PostMapping("/uploads")
    public String uploads(MultipartFile[] imgs, HttpServletRequest request){
        //定义一个集合存储文件预览时的路径
        List<String> files=new ArrayList<>();
        for (MultipartFile img:imgs){
            //文件不为空，保存文件
            if (img.getSize()>0){
                //获取文件保存的绝对路径file
                String path=request.getServletContext().getRealPath("file");
                //获取上传的文件名
                String name=img.getOriginalFilename();
                //在要保存的路径内创建一个空的文件，文件名为字段 name
                File folder=new File(path);
                if (!folder.exists()){
                    folder.mkdir();
                }
                File file=new File(path,name);
                //将 img 的 数据 赋到该空文件中
                try {
                    img.transferTo(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                //保存上传之后的文件路径到list集合
                files.add("/file/"+name);
            }
            //将list集合传回前端
            request.setAttribute("files", files);
        }
        return "uploads";
    }

    @GetMapping("/download/{name}")
    public void download(@PathVariable("name") String name, HttpServletRequest request, HttpServletResponse response){
        //判断参数是否为null
        if(name!=null){
            //获取文件绝对路径
            String path=request.getServletContext().getRealPath("file");
            //获取要下载的文件
            File file =new File(path,name+".png");
            //创建输出流用于下载文件
            OutputStream stream=null;
            //判断文件是否存在
            if(file.exists()){
                //设置下载需要的属性
                response.setContentType("application/force-download");
                response.setHeader( "Content-Disposition","attachment;filename="+name+".png");
                try {
                    //对接设置的属性
                    stream=response.getOutputStream();
                    //文件写入，完成下载
                    stream.write(FileUtils.readFileToByteArray(file));
                    stream.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    //关闭输出流
                    if(stream != null){
                        try {
                            stream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
