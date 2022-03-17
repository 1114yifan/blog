package com.yf.blog.service.impl;

import com.yf.blog.entity.Blog;
import com.yf.blog.exception.NotFoundException;
import com.yf.blog.mapper.BlogMapper;
import com.yf.blog.queryvo.*;
import com.yf.blog.service.BlogService;
import com.yf.blog.utils.MarkdownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @Description:
 */
@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogMapper blogMapper;


    @Override
    public ShowBlog getBlogById(Long id) {
        return blogMapper.getBlogById(id);
    }

    @Override
    public List<BlogQuery> getAllBlog() {
        return blogMapper.getAllBlogQuery();
    }

    @Transactional
    @Override
    public int saveBlog(Blog blog) {
        blog.setCreateTime(new Date());
        blog.setUpdateTime(new Date());
        // 初始化浏览记录为0
        blog.setViews(0);
        // 初始化
        blog.setCommentCount(0);
        return blogMapper.saveBlog(blog);
    }

    @Transactional
    @Override
    public int updateBlog(ShowBlog showBlog) {
        showBlog.setUpdateTime(new Date());
        return blogMapper.updateBlog(showBlog);
    }

    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogMapper.deleteBlog(id);
    }

    @Override
    public List<BlogQuery> getBlogBySearch(SearchBlog searchBlog) {
        return blogMapper.searchByTitleOrTypeOrRecommend(searchBlog);
    }

    @Override
    public List<FirstPageBlog> getAllFirstPageBlog() {
        return blogMapper.getAllFirstPageBlog();
    }

    @Override
    public List<RecommendBlog> getRecommendedBlog() {
        return blogMapper.getRecommendedBlog();
    }

    @Override
    public List<FirstPageBlog> getSearchBlog(String query) {
        return blogMapper.getSearch(query);
    }

    @Transactional
    @Override
    public DetailedBlog getDetailedBlog(Long id) {
        DetailedBlog detailedBlog = blogMapper.getDetailedBlog(id);
        if (detailedBlog == null) {
            throw new NotFoundException("该博客不存在");
        }
        String content = detailedBlog.getContent();
        // 将markdown格式转换成html
        detailedBlog.setContent(MarkdownUtils.markdownToHtmlExtensions(content));
        // 处理文章访问数量自增
        blogMapper.updateViews(id);
        // 处理文章品论数量更新
        blogMapper.getCommentCountById(id);
        return detailedBlog;
    }

    @Override
    public List<FirstPageBlog> getByTypeId(Long id) {
        return blogMapper.getByTypeId(id);
    }
}
