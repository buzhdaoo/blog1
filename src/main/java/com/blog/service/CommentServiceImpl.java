package com.blog.service;

import com.blog.dao.BlogDao;
import com.blog.dao.CommentDao;
import com.blog.po.Comment;
import com.blog.util.CycleComment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/28/028 Description:
 **/
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentDao commentDao;

    @Autowired
    private BlogDao blogDao;


    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        //查询出父节点
        Sort sort=Sort.by (Sort.Direction.ASC,"createTime");
        List<Comment>comments=commentDao.findByBlogIdAndParentCommentNull (blogId,sort);

        return CycleComment.eachComment(comments) ;
    }

    //    新增评论
    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentComment=comment.getParentComment ().getId ();
        if (parentComment != -1){
            comment.setParentComment (commentDao.getById (parentComment));

        }else {
            comment.setParentComment (null);
        }

        comment.setCreateTime (new Date ());
        return commentDao.save (comment);
    }
    @Override
    public Long countComment() {
        return (long) commentDao.countComment ( );
    }
    //开启事务
    @Transactional
    @Override
    public void deleteComment(Long id ) {
        commentDao.deleteById (id);
    }

}