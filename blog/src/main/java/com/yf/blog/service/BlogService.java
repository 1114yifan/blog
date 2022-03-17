package com.yf.blog.service;

import com.github.pagehelper.PageInfo;
import com.yf.blog.entity.Blog;
import com.yf.blog.queryvo.*;

import java.util.List;

/**
 * @Description:
 */
public interface BlogService {

    ShowBlog getBlogById(Long id);

    List<BlogQuery> getAllBlog();

    int saveBlog(Blog blog);

    int updateBlog(ShowBlog showBlog);

    void deleteBlog(Long id);

    List<BlogQuery> getBlogBySearch(SearchBlog searchBlog);

    // 首页展示的博客
    List<FirstPageBlog> getAllFirstPageBlog();

    // 首页展示推荐文章
    List<RecommendBlog> getRecommendedBlog();

    List<FirstPageBlog> getSearchBlog(String query);

    // 获取博客详情
    DetailedBlog getDetailedBlog(Long id);

    // 根据分类id查询所有该分类的博客
    List<FirstPageBlog> getByTypeId(Long id);
}
