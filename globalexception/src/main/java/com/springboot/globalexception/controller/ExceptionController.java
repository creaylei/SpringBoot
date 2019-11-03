package com.springboot.globalexception.controller;

import com.springboot.globalexception.MyException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class ExceptionController {
    //自定义测试用controller
    @GetMapping("test")
    public String test(Integer num) {
        if (num == null) {
            throw new MyException(400, "num不能为空");
        }
        return num.toString();
    }

    @GetMapping("hello")
    public String hello(Model model) {
        Map<String, Object> map = model.asMap();
        System.out.println(map);
        int i = 1/0;
        return "hello controller advice";
    }
}
