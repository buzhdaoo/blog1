package com.blog.web.admin;

import com.blog.po.User;
import com.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * @Author:Administrator
 * @DATE:2022/1/25/025 Description:
 **/
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String loginPage() {
        return "admin/login";
    }
    //登陆验证
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes) {
        User user = userService.checkUser (username, password);
        if (user != null) {
            user.setPassword (null);
            session.setAttribute ("user", user);
            return "admin/index";
        } else {
            //重定向使用Mode前台拿不到值
            attributes.addFlashAttribute ("message","用户名或密码错误");
            return "redirect:/admin";
        }
    }
    //注销
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute ("user");
        return "redirect:/admin";
    }
}
