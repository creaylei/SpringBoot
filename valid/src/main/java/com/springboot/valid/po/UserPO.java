package com.springboot.valid.po;

import com.springboot.valid.utils.Groups;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserPO {

    @NotNull(message = "id 不能为空", groups = Groups.Update.class)
    private Long id;

    @NotBlank(message = "用户名不允许为空", groups = Groups.Default.class)
    @Length(min = 2, max = 10, message = "用户名长度介于 {min} - {max} 之间")
    private String username;

    @NotNull(message = "密码不允许为空", groups = Groups.Default.class)
    private String password;
}
