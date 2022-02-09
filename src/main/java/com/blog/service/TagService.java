package com.blog.service;

import com.blog.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {

    //新增
    Tag saveTag(Tag tag);
    //查询
    Tag getTag(Long id);
    //分页查询
    Page<Tag> listTag(Pageable pageable);
    //修改
    Tag updateTag(Long id,Tag tag);
    //删除
    void deleteTag(Long id);
    //通过名称查询type
    Tag getTagByName(String name);
    //获取所有的Tag
    List<Tag>listTag();

    //根据id集合获取对象集合
    List<Tag>listTag(String ids);

    //获取页数
    List<Tag> listTagTop(Integer size);
}
