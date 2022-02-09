package com.blog.service;

import com.blog.po.Blog;
import com.blog.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {

    //查询
    Blog getBlog(Long id);
    //分页查询
    Page<Blog>listBlog(Pageable pageable, BlogQuery blog);
    //新增
    Blog saveBlog(Blog blog);
    //更新
    Blog updateBlog(Long id,Blog blog);
    //删除
    void deleteBlog(Long id);
    //只传递一个方法
    Page<Blog> listBlog(Pageable pageable);

    //
    List<Blog>listBlogTop(Integer size);
    //传递2个值
    Page<Blog> listBlog(String query ,Pageable pageable);

    //用于前端展示
    Blog getAndConvert(Long id);

    //查询标签，需要2个值
    Page<Blog> listBlog(Pageable pageable,Long tagId);

    Map<String,List<Blog>>archivesBlog();

    Long countBlog();


}
