package com.yf.blog.service;

import com.github.pagehelper.PageInfo;
import com.yf.blog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @Description:
 */
public interface TypeService {
    /**
     * 增加一个分类
     * @param type
     * @return
     */
    int saveType(Type type);

    /**
     * 根据id删除一个分类
     * @param id
     * @return
     */
    int deleteType(Long id);

    /**
     * 更新一个分类专栏
     * @param type
     * @return
     */
    int updateType(Type type);

    /**
     * 查询所有分类
     * @return
     */
    List<Type> getAllType();

    /**
     * 分页显示
     * @return
     */
    PageInfo<Type> getAllTypeByPages(int pageNum,int pageSize);

    /**
     * 首页右侧展示type对应的博客数量
     * @return
     */
    List<Type> getBlogType();


    /**
     * 根据名字查询
     * @return
     */
    Type getTypeByName(String name);

    /**
     * 根据ID查询
     * @return
     */
    Type getType(Long id);
}
