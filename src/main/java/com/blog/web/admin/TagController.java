package com.blog.web.admin;

import com.blog.po.Tag;
import com.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @Author:Administrator
 * @DATE:2022/1/26/026 Description:
 **/
@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;
    @GetMapping("/tags")
    public String list(@PageableDefault(size = 4,//分页数量
                        sort = {"id"},//根据id分页
                        direction = Sort.Direction.DESC)//排序方式：倒序
                         Pageable pageable , Model model) {
        model.addAttribute ("page", tagService.listTag (pageable));

        return "admin/tags";
    }

    //新增
    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute ("tag",new Tag ());
        return "admin/tags-input";
    }
    //提交
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result,RedirectAttributes attributes){
        Tag tag2=tagService.getTagByName (tag.getName ());
        if (tag2 != null){
                result.rejectValue ("name","nameERROR","该标签已存在！！！");
        }

        //如果校验有错误，返回"admin/types-input";页面
        if (result.hasErrors ()){
            return "admin/tags-input";
        }
        Tag tag1 = tagService.saveTag (tag);
        if (tag1==null){
            attributes.addFlashAttribute ("message","新增失败！！！");
        }else {
            attributes.addFlashAttribute ("message","新增成功！！！");
        }
        return "redirect:/admin/tags";
    }

    //修改页面
    @GetMapping("/tags/{id}/input")
    public String editInput(@PathVariable Long id, Model model){

        model.addAttribute ("tag",tagService.getTag(id));

        return "admin/tags-input";
    }
    //修改
    @PostMapping("/tags/{id}")
    public String editPost(@Valid Tag tag, BindingResult result, @PathVariable Long id, RedirectAttributes attributes){
        Tag tag3=tagService.getTagByName (tag.getName ());
        if (tag3 != null){
            result.rejectValue ("name","nameERROR","该标签已存在！！！");
        }

        //如果校验有错误，返回"admin/types-input";页面
        if (result.hasErrors ()){
            return "admin/tags-input";
        }
        Tag tag1 = tagService.updateTag (id,tag);
        if (tag1==null){
            attributes.addFlashAttribute ("message","更新失败！！！");
        }else {
            attributes.addFlashAttribute ("message","更新成功！！！");
        }
        return "redirect:/admin/tags";
    }
    //删除
    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){
        tagService.deleteTag (id);
        attributes.addFlashAttribute ("message","删除成功！！！");
        return "redirect:/admin/tags";
    }
}