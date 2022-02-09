package com.blog.po;

import org.springframework.validation.annotation.Validated;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/25/025 Description:类型实体类
 **/
@Validated
@Entity(name ="t_type")
@Table
public class Type {
    @Id
    @GeneratedValue
    private Long id;
    //非空校验
    @NotBlank(message = "分类名称不能为空！！！")
    @Valid
    private String name;

    @OneToMany(mappedBy = "type")
    private List<Blog>blogs=new ArrayList<> ();
    public Type(){
    }

    public List<Blog> getBlogs() {
        return blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        this.blogs = blogs;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
