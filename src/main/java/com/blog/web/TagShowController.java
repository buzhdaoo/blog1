package com.blog.web;

import com.blog.po.Tag;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @Author:Administrator
 * @DATE:2022/1/28/028 Description:
 **/
@Controller
public class TagShowController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    @GetMapping("/tags/{id}")
    public String types(@PageableDefault(size = 4, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model,@PathVariable Long id){

        List<Tag>tags=tagService.listTagTop (6666);

        if (id == -1){
            id = tags.get (0).getId ();
        }
        model.addAttribute ("tags",tags);
        model.addAttribute ("page",blogService.listBlog (pageable,id));
        model.addAttribute ("activeTagId",id);
        return "tags";
    }

}
