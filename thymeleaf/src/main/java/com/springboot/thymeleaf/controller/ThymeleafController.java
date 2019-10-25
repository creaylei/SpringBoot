package com.springboot.thymeleaf.controller;

import com.springboot.thymeleaf.po.Author;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ThymeleafController {

    @GetMapping("/springboot/index")
    public ModelAndView index() {
        ModelAndView view = new ModelAndView();
        //设置跳转的视图  默认映射到 src/main/resources/templates/{viewName}.html
        view.setViewName("index");

        //设置属性
        view.addObject("title", "我的第一个web页面");
        view.addObject("desc", "SpringBoot集成Thymeleaf");

        Author author = new Author().setAge(25).setName("creaylei").setEmail("984700833@qq.com");
        view.addObject("author", author);

        return view;
    }

    /**
     * 第二种写法
     *
     * @param request
     * @return
     */
    @GetMapping("/index1")
    public String index1(HttpServletRequest request) {
        //设置属性
        request.setAttribute("title", "我的第一个web页面");
        request.setAttribute("desc", "SpringBoot集成Thymeleaf");
        Author author = new Author().setAge(25).setName("creaylei").setEmail("984700833@qq.com");
        request.setAttribute("author", author);
        // 返回的 index 默认映射到 src/main/resources/templates/xxxx.html
        return "index";
    }

}
