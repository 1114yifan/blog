package com.yf.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.yf.blog.entity.Type;
import com.yf.blog.queryvo.FirstPageBlog;
import com.yf.blog.service.BlogService;
import com.yf.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @Description: 前端展示分类控制器
 */
@Controller
public class TypeShowController {

    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;

    @GetMapping("/types/{id}")
    public String types(Model model, @RequestParam(defaultValue = "1", value = "pageNum") Integer pageNum,
                        @PathVariable Long id){
        List<Type> types = typeService.getBlogType();
        // -1表示空，是从主页进入的
        if (id == -1) {
            id = types.get(0).getId();
        }
        model.addAttribute("types",types);
        List<FirstPageBlog> blogs = blogService.getByTypeId(id);
        PageHelper.startPage(pageNum,10);
        PageInfo<FirstPageBlog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo",pageInfo);
        model.addAttribute("activeTypeId",id);
        return "types";
    }

}
