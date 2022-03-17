package com.yf.blog.service;

import com.yf.blog.entity.Comment;

import java.util.List;

/**
 * @Description:
 */
public interface CommentService {


    // 所有评论
    List<Comment> listCommentByBlogId(Long id);

    // 新增评论
    int saveComment(Comment comment);

    // 删除评论
    void deleteComment(Comment comment, Long id);

}
