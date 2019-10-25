package com.springboot.valid.controller;

import com.springboot.valid.po.UserPO;
import org.hibernate.validator.constraints.Length;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

    @GetMapping("/test1")
    public String test(@NotBlank(message = "用户名不允许为空")
                       @Length(min = 2, max = 10, message = "用户名长度介于 {min} - {max} 之间")String name) {
        return "success";
    }

    @GetMapping("/test2")
    public String test2(@Validated UserPO userPO) {
        return "success";
    }
}
