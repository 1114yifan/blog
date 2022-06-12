package com.yf.blog.service.impl;

import com.yf.blog.entity.Comment;
import com.yf.blog.mapper.BlogMapper;
import com.yf.blog.mapper.CommentMapper;
import com.yf.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PreDestroy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description:
 */
@Service
public class CommentServiceImpl implements CommentService {

    // 存放迭代找出的所有子级评论集合
    private List<Comment> tempReplays = new ArrayList<>();

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private BlogMapper blogMapper;

    @Transactional
    @Override
    public int saveComment(Comment comment) {
        comment.setCreateTime(new Date());
        int i = commentMapper.saveComment(comment);
        /*更新博客的评论数量*/
        blogMapper.getCommentCountById(comment.getBlogId());
        return i;
    }

    @Transactional
    @Override
    public void deleteComment(Comment comment, Long id) {
        commentMapper.deleteComment(id);
        /*更新博客的评论数量*/
        blogMapper.getCommentCountById(comment.getBlogId());
    }

    @Override
    public List<Comment> listCommentByBlogId(Long blogId) {
        // 查询顶级评论
        List<Comment> comments = commentMapper.findByBlogIdParentIdNull(blogId, Long.parseLong("-1"));

        // 循环遍历每条顶级评论
        for (Comment comment : comments) {
            // 顶级评论id   顶级评论人大名
//            Long id = comment.getId();
//            String parentNickname = comment.getNickname();
            // 根据评论id查询一级回复
//            List<Comment> childComments = commentMapper.findByBlogIdParentIdNotNull(blogId, id);
            List<Comment> childComments = commentMapper.findByBlogIdParentIdNull(blogId, comment.getId());
            // 查询出子级评论
//            combineChildren(blogId,childComments,parentNickname);
            recursively(comment,childComments);
            // set子级评论集合
            comment.setReplyComments(tempReplays);
            /*清空评论集合*/
            tempReplays = new ArrayList<>();
        }
        return comments;
    }

    /**
     * 查询一级子级评论,调用方法查询二级子级评论
     * @param blogId
     * @param childComments 一级回复集合
     * @param parentNickname 父级评论人大名
     */
    /*private void combineChildren(Long blogId, List<Comment> childComments, String parentNickname) {

        if (childComments.size() > 0) {
            // 如果存在一级子评论吗,遍历
            for (Comment childComment : childComments) {
                // 获取一级子评论人大名
                String nickname = childComment.getNickname();
                // set一级子评论的父级评论的name属性
                childComment.setParentNickname(parentNickname);
                // 将一级子评论添加到集合中
                tempReplays.add(childComment);
                // 获取以及子评论的id
                Long childId = childComment.getId();
                // 查询二级子评论
                recursively(blogId,childId,nickname);
            }
        }
    }*/

    /**
     * 递归迭代,剥洋葱.    查询子级评论
//     * @param blogId
//     * @param childId 上级评论id
//     * @param parentNickname 上级子评论人大名
     */
//    private void recursively(Long blogId, Long childId, String parentNickname) {
    private void recursively(Comment comment, List<Comment> comments) {
        // 通过上级子评论id获取下级子级评论集合
//        List<Comment> replayComments = commentMapper.findByBlogIdParentIdNull(comment.getBlogId(), comment.getParentCommentId());

        if (comments.size() > 0) {
            // 如果存在子级评论,遍历
            for (Comment replayComment : comments) {
                // 获取评论姓名
//                String nickname = replayComment.getNickname();
                // set设置上级评论姓名
                replayComment.setParentNickname(comment.getNickname());
                // 获取评论id
//                Long replayId = replayComment.getId();
                // 将子级评论添加到集合
                tempReplays.add(replayComment);
                // 递归调用剩余评论
                List<Comment> litterComment = commentMapper.findByBlogIdParentIdNull(replayComment.getBlogId(), replayComment.getId());
                recursively(replayComment,litterComment);
            }
        }
    }
}
