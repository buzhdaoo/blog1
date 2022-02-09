package com.blog.dao;

import com.blog.po.Blog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/26/026 Description:
 **/
public interface BlogDao extends JpaRepository<Blog,Long>, JpaSpecificationExecutor<Blog> {//JpaSpecificationExecutor:动态查询
    @Query("SELECT b from t_blog b where  b.recommend=true")
    List<Blog>findTop(Pageable pageable);
    @Query("select b from  t_blog b where b.title like  ?1 or b.content like ?1 ")
    Page<Blog>findByQuery(String query,Pageable pageable);
    @Transactional
    @Modifying
    @Query("update t_blog b set  b.views =b.views+1 where  b.id=?1")
    void updateViews(Long id);

    @Query("select function('date_format',b.updateTime,'%Y') " +
            "as year from t_blog b group by " +
            "function('date_format',b.updateTime,'%Y') order by year desc")
    List<String> findGroupYear();
    @Query("select b from t_blog b where function('date_fromat',b.updateTime,'%Y') = ?1 ")
    List<Blog>findByYear(String year);
}
