package com.yf.blog.controller;

import com.yf.blog.queryvo.BlogQuery;
import com.yf.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @Description:
 */
@Controller
public class AboutController {

    @GetMapping("/about")
    public String archives() {
        return "about";
    }

}
