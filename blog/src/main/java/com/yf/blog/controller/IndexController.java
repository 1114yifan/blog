package com.yf.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.blog.entity.Comment;
import com.yf.blog.entity.Type;
import com.yf.blog.mapper.BlogMapper;
import com.yf.blog.queryvo.*;
import com.yf.blog.service.BlogService;
import com.yf.blog.service.CommentService;
import com.yf.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @Description: 首页控制器
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @Autowired
    private TypeService typeService;

    @Autowired
    private CommentService commentService;

    // 分页查询博客列表
    @GetMapping("/")
    public String index(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum){
        List<FirstPageBlog> allFirstPageBlog = blogService.getAllFirstPageBlog();
        List<RecommendBlog> recommendBlogs = blogService.getRecommendedBlog();
        List<Type> blogType = typeService.getBlogType();
        PageHelper.startPage(pageNum, 10);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(allFirstPageBlog);
        /*首页分页文章列表*/
        model.addAttribute("pageInfo", pageInfo);
        /*4条推荐文章*/
        model.addAttribute("recommendBlogs", recommendBlogs);
        /*分类*/
        model.addAttribute("types", blogType);
        return "index";
    }

    // 查询的方法
    @PostMapping("/search")
    public String search(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                         @RequestParam String query){
        PageHelper.startPage(pageNum,10);
        List<FirstPageBlog> searchBlog = blogService.getSearchBlog(query);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(searchBlog);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("query",query);
        return "search";
    }

    // 博客详情
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        // 获取博客详情,且自增访问次数
        DetailedBlog detailedBlog = blogService.getDetailedBlog(id);
        //  同时加载评论
        List<Comment> comments = commentService.listCommentByBlogId(id);
        model.addAttribute("comments", comments);
        model.addAttribute("blog", detailedBlog);
        return "blog";
    }

    //
    @GetMapping("/footer/newblog")
    public String newblogs(Model model){
        List<RecommendBlog> newblogs = blogService.getRecommendedBlog();
        model.addAttribute("newblogs",newblogs);
        return "_fragments :: newblogList";
    }
}
