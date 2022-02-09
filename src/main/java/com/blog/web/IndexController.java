package com.blog.web;

import com.blog.po.Blog;
import com.blog.service.BlogService;
import com.blog.service.TagService;
import com.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
public class IndexController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @GetMapping("/")
    public String index(@PageableDefault(size = 4, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model){
        Page<Blog> blogPage = blogService.listBlog (pageable);
        model.addAttribute("page", blogPage);
        model.addAttribute("types", typeService.listTypeTop(4));
        model.addAttribute("tags", tagService.listTagTop(8));
        model.addAttribute("recommendBlogs", blogService.listBlogTop(4));
        return "index";
    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 6, sort = {"updateTime"},
                        direction = Sort.Direction.DESC) Pageable pageable,
                         Model model ,@RequestParam String query){
        model.addAttribute ("page",blogService.listBlog("%"+query+"%",pageable));
        model.addAttribute ("query",query);

        return "search";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id, Model model){
        model.addAttribute ("blog",blogService.getAndConvert (id));
        return "blog";
    }
}
