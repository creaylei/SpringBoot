package com.springboot.globalexception.controller;

import com.springboot.globalexception.MyException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
