package com.blog.web;

import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @Author:Administrator
 * @DATE:2022/1/28/028 Description:
 **/
@Controller
public class ArchivesShowController {
    @Autowired
    private BlogService blogService;
    @GetMapping("/archives")
    public String archives(Model model){
        model.addAttribute (blogService.archivesBlog ());

        model.addAttribute ("blogCount",blogService.countBlog ());
        return "archives";
    }
}
