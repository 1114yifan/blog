package com.yf.blog.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.blog.entity.Type;
import com.yf.blog.mapper.TypeMapper;
import com.yf.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @Description:
 */
@Service
public class TypeServiceImpl implements TypeService {

    @Autowired
    private TypeMapper typeMapper;

    @Transactional  // 事务处理
    @Override
    public int saveType(Type type) {
        return typeMapper.saveType(type);
    }

    @Transactional
    @Override
    public int deleteType(Long id) {
        return typeMapper.deleteType(id);
    }

    @Transactional
    @Override
    public int updateType(Type type) {
        return typeMapper.updateType(type);
    }

    @Override
    public List<Type> getAllType() {
        return typeMapper.getAllType();
    }

    @Override
    public PageInfo<Type> getAllTypeByPages(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum,pageSize);
        List<Type> typeList = typeMapper.getAllType();
        PageInfo<Type> pageInfo = new PageInfo<>(typeList);
        return pageInfo;
    }

    @Override
    public List<Type> getBlogType() {
        return typeMapper.getBlogType();
    }

    @Override
    public Type getTypeByName(String name) {
        return typeMapper.getTypeByName(name);
    }

    @Override
    public Type getType(Long id) {
        return typeMapper.getType(id);
    }
}
