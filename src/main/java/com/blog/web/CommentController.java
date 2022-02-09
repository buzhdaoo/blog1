package com.blog.web;

import com.blog.po.Comment;
import com.blog.po.User;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/28/028 Description:
 **/
@Controller
public class CommentController {
    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @Value ("${comment.avatar}")
    private String avatar;

    //   查询评论列表
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model) {
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("comments", comments);
        return "blog :: commentList";
    }

    //    新增评论
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session, Model model) {
            Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));

        User user = (User) session.getAttribute("user");
        if(user != null) {
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            comment.setAvatar(avatar);
            comment.setAdminComment(false);
        }
        commentService.saveComment(comment);
        return "redirect:/comments/" + comment.getBlog().getId();

    }

    //    删除评论
    @GetMapping("/comments/{id}delete")
    public String delete(@PathVariable Long id,RedirectAttributes redirectAttributes ){
        commentService.deleteComment (id);
        redirectAttributes.addFlashAttribute ("message","删除成功！！！");

        return "redirect:/comments";

    }
}