package com.blog.dao;

import com.blog.po.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


/**
 * @Author:Administrator
 * @DATE:2022/1/26/026 Description:
 **/
public interface TagDao  extends JpaRepository<Tag, Long> {
    Tag findByName(String name);
    //自定义查询
    @Query("select t from t_tag t")
    List<Tag> findTop(Pageable pageable);
}
