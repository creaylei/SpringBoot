package com.springboot.valid.controller;

import com.springboot.valid.po.UserPO;
import com.springboot.valid.utils.Groups;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ValidateController {

    @GetMapping("/insert")
    public String insert(@Validated(value = Groups.Default.class)UserPO userPO) {
        return "insert";
    }

    @GetMapping("/update")
    public String update(@Validated(value = {Groups.Update.class, Groups.Default.class})UserPO userPO) {
        return "insert";
    }
}
