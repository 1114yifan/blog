package com.yf.blog.mapper;

import com.github.pagehelper.PageInfo;
import com.yf.blog.entity.Blog;
import com.yf.blog.queryvo.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description: 博客持久层接口
 */
@Repository
public interface BlogMapper {

    ShowBlog getBlogById(Long id);

    List<Blog> getAllBlog();

    List<BlogQuery> getAllBlogQuery();

    int saveBlog(Blog blog);

    int updateBlog(ShowBlog showBlog);

    void deleteBlog(Long id);

    List<BlogQuery> searchByTitleOrTypeOrRecommend(SearchBlog searchBlog);

    List<FirstPageBlog> getAllFirstPageBlog();

    List<RecommendBlog> getRecommendedBlog();

    List<FirstPageBlog> getSearch(String query);

    DetailedBlog getDetailedBlog(Long id);

    int updateViews(Long id);

    // 查询评论数量
    int getCommentCountById(Long id);

    List<FirstPageBlog> getByTypeId(Long typeId);
}
