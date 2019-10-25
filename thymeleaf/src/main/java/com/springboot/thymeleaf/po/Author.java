package com.springboot.thymeleaf.po;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Author {

    private Integer age;

    private String name;

    private String email;
}
