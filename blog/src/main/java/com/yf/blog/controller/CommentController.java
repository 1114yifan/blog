package com.yf.blog.controller;

import com.yf.blog.entity.Comment;
import com.yf.blog.entity.User;
import com.yf.blog.queryvo.DetailedBlog;
import com.yf.blog.service.BlogService;
import com.yf.blog.service.CommentService;
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
 * @Description: 评论控制器
 */
@Controller
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    /**
     * 返回评论信息片段
     * @param blogId
     * @param model
     * @return
     */
    @GetMapping("/comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){
        model.addAttribute("comments","");
        return "blog :: commentList";
    }

    /**
     * 发布评论
     * @param comment
     * @return
     */
    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session, Model model){
        Long blogId = comment.getBlogId();
        User user = (User) session.getAttribute("user");
        if (user != null) { /*是否为管理员评论*/
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
        } else {
            /*不是则设置普通访客的头像*/
            comment.setAvatar(avatar);
        }
        if (comment.getParentComment().getId() != null) {   /*不是一级评论则设置父级评论id*/
            comment.setParentCommentId(comment.getParentComment().getId());
        }
        commentService.saveComment(comment);
        model.addAttribute("comments", commentService.listCommentByBlogId(blogId));
        return "blog :: commentList";
    }

    /**
     * 删除评论
     * @param blogId 博客id
     * @param id 评论id
     * @param comment 评论内容
     * @param attributes
     * @param model
     * @return
     */
    @GetMapping("/comment/{blogId}/{id}/delete")
    public String delete(@PathVariable Long blogId, @PathVariable Long id, Comment comment,
                         RedirectAttributes attributes, Model model){
        commentService.deleteComment(comment,id);
        DetailedBlog detailedBlog = blogService.getDetailedBlog(blogId);
        List<Comment> comments = commentService.listCommentByBlogId(blogId);
        model.addAttribute("blog",detailedBlog);
        model.addAttribute("comments",comments);
        return "blog";
    }

}
