package com.blog.util;

import com.blog.po.Comment;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/28/028 Description:
 **/
public  class CycleComment {


    //存放迭代找出的所有子代的集合
  private static List<Comment> tempReplys = new ArrayList<> ();
    //循环每个顶级的评论节点
   public static List<Comment> eachComment(List<Comment> comments) {
        //复制数据
        List<Comment> commentsView = new ArrayList<> ();
        for (Comment comment : comments) {
            Comment c = new Comment();
            BeanUtils.copyProperties(comment,c);
            commentsView.add(c);
        }
        //合并评论的各层子代到第一级子代集合中
        combineChildren(commentsView);
        return commentsView;
    }
    // comments root根节点，blog不为空的对象集合
  public static void combineChildren(List<Comment> comments) {

        for (Comment comment : comments) {
            List<Comment> replys1 = comment.getReplyComments();
            for(Comment reply1 : replys1) {
                //循环迭代，找出子代，存放在tempReplys中
                recursively(reply1);
            }
            //修改顶级节点的reply集合为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys = new ArrayList<>();
        }
    }
    public static void recursively(Comment comment) {
        tempReplys.add (comment);//顶节点添加到临时存放集合
        if (comment.getReplyComments ( ).size ( ) > 0) {
            List<Comment> replys = comment.getReplyComments ( );
            for (Comment reply : replys) {
                tempReplys.add (reply);
                if (reply.getReplyComments ( ).size ( ) > 0) {
                    recursively (reply);
                }
            }
        }
    }
}
