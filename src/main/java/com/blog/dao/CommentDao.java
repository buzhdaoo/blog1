package com.blog.dao;


import com.blog.po.Comment;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentDao extends JpaRepository<Comment,Long> {

    List<Comment>findByBlogIdAndParentCommentNull(Long blogId, Sort sort);
    @Query("select COUNT(c.id) from t_comment c")
    int countComment();
}
