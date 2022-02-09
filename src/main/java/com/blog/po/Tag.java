package com.blog.po;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/25/025 Description:标签实体类
 **/
@Entity(name = "t_tag")
@Table
public class Tag {
    @Id
    //生成策略
    @GeneratedValue
    private Long id;
    @NotBlank(message = "分类名称不能为空！！！")
    @Valid
    private String name;

    @ManyToMany(mappedBy = "tags")
    private List<Blog>blogs=new ArrayList<> ();

    public Tag() {
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
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
