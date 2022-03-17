package com.yf.blog.controller.admin;

import com.yf.blog.entity.Type;
import com.yf.blog.service.TypeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

/**
 * @Description:
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    private TypeService typeService;

    /**
     * 分页显示
     */
    @GetMapping("/types")
    public String types(@RequestParam(required = false,defaultValue = "1",value = "pageNum") int pageNum, Model model) {
        PageInfo<Type> pageInfo = typeService.getAllTypeByPages(pageNum, 5);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String toAddType(){
        return "admin/types-input";
    }

    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable Long id, Model model){
        model.addAttribute("type", typeService.getType(id));
        return "admin/types-input";
    }

    /**
     * 增加
     */
    @PostMapping("/types")
    public String addType(Type type, RedirectAttributes attributes){
        String typeName = type.getName();
        Type t = typeService.getTypeByName(typeName);
        if (t == null) {
            typeService.saveType(type);
            attributes.addFlashAttribute("message","操作成功");
            // 不能直接跳转到types页面,没有执行types方法跳转会没有数据
            return "redirect:/admin/types";
        } else {
            // 分类已存在
            attributes.addFlashAttribute("name",typeName);
            attributes.addFlashAttribute("message","你输入的分类已存在");
        }
        // 输入为空
        if (typeName == null || typeName.equals("")) {
            attributes.addFlashAttribute("message","你还没输入内容呢");
        }
        // 添加失败跳转回去
        return "redirect:/admin/types/input";
    }

    /**
     * 删除
     */
    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable Long id, RedirectAttributes attributes){
        typeService.deleteType(id);
        attributes.addFlashAttribute("message","删除成功");
        return "redirect:/admin/types";
    }

    /**
     * 修改
     */
    @PostMapping("/types/{id}")
    public String update(@PathVariable Long id, Type type, RedirectAttributes attributes){
        String name = type.getName();
        Type t = typeService.getTypeByName(name);
        if (t == null) {
            typeService.updateType(type);
            attributes.addFlashAttribute("message","修改成功");
            return "redirect:/admin/types";
        } else {
            // 分类已存在
            attributes.addFlashAttribute("name",name);
            attributes.addFlashAttribute("message","你输入的分类已存在");
        }
        // 输入为空
        if (name == null || name.equals("")) {
            attributes.addFlashAttribute("message","你还没输入内容呢");
        }
        // 添加失败跳转回去
        return "redirect:/admin/types/input";
    }

}
