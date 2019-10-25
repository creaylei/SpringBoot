# SpringBoot进行数据校验

## @Validated

先看一个代码进行数据校验的例子

```java
public String test1(String name) {
    if (name == null) {
        throw new NullPointerException("name 不能为空");
    }
    if (name.length() < 2 || name.length() > 10) {
        throw new RuntimeException("name 长度必须在 2 - 10 之间");
    }
    return "success";
}
```

冗长，而又逻辑繁琐，那如何轻松搞定

### 快速使用

#### 引入依赖

```xml
<dependencies>
 <dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
```

默认的starter中就带了

#### 创建PO类

```java
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class UserPO {

    private Long id;

    @NotBlank(message = "用户名不允许为空")
    @Length(min = 2, max = 10, message = "用户名长度介于 {min} - {max} 之间")
    private String username;

    @NotNull(message = "密码不允许为空")
    private String password;
}
```

#### 创建Controller

```java
//为了体现 validation 的强大，分别演示

@RestController
@RequestMapping("/users")
@Validated
public class UserController {

  //1. 普通参数属性验证
    @GetMapping("/test1")
    public String test(@NotBlank(message = "用户名不允许为空")
                       @Length(min = 2, max = 10, message = "用户名长度介于 {min} - {max} 之间")String name) {
        return "success";
    }

  //2. 对象的验证
    @GetMapping("/test2")
    public String test2(@Validated UserPO userPO) {
        return "success";
    }
}
```

- @Validated用在类上，校验所有方法
- 用在方法参数中纪委验证参数对象，用在方法上无效

#### 效果

![KUEQTs.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/KUEQTs.png)

![KUEiTA.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/KUEiTA.png)

#### 附录

这里只列举了 `javax.validation` 包下的注解，同理在 `spring-boot-starter-web` 包中也存在 `hibernate-validator` 验证包，里面包含了一些 `javax.validation` 没有的注解，有兴趣的可以看看

|            注解             |                             说明                             |
| :-------------------------: | :----------------------------------------------------------: |
|         `@NotNull`          |                     **限制必须不为null**                     |
|         `@NotEmpty`         | **验证注解的元素值不为 null 且不为空（字符串长度不为0、集合大小不为0）** |
|         `@NotBlank`         | **验证注解的元素值不为空（不为null、去除首位空格后长度为0），不同于@NotEmpty，@NotBlank只应用于字符串且在比较时会去除字符串的空格** |
|      `@Pattern(value)`      |               **限制必须符合指定的正则表达式**               |
|      `@Size(max,min)`       |  **限制字符长度必须在 min 到 max 之间（也可以用在集合上）**  |
|          `@Email`           | **验证注解的元素值是Email，也可以通过正则表达式和flag指定自定义的email格式** |
|        `@Max(value)`        |             **限制必须为一个不大于指定值的数字**             |
|        `@Min(value)`        |             **限制必须为一个不小于指定值的数字**             |
|    `@DecimalMax(value)`     |             **限制必须为一个不大于指定值的数字**             |
|    `@DecimalMin(value)`     |             **限制必须为一个不小于指定值的数字**             |
|           `@Null`           |                 **限制只能为null（很少用）**                 |
|       `@AssertFalse`        |                **限制必须为false （很少用）**                |
|        `@AssertTrue`        |                **限制必须为true （很少用）**                 |
|           `@Past`           |                 **限制必须是一个过去的日期**                 |
|          `@Future`          |                 **限制必须是一个将来的日期**                 |
| `@Digits(integer,fraction)` | **限制必须为一个小数，且整数部分的位数不能超过 integer，小数部分的位数不能超过 fraction （很少用）** |

## 分组验证

有的时候，我们对一个实体类需要有多中验证方式，在不同的情况下使用不同验证方式，比如说对于一个实体类来的 id 来说，新增的时候是不需要的，对于更新时是必须的，这个时候你是选择写一个实体类呢还是写两个呢？ 在[自定有数据有效性校验注解](http://blog.battcn.com/2018/06/06/springboot/v2-other-validate2/)中介绍到注解需要有一个 `groups` 属性，这个属性的作用又是什么呢？ 接下来就让我们看看如何用一个验证类实现多个接口之间不同规则的验证…

### 目标

利用一个验证类实现多个接口之间不同规则的验证…

### 快速使用

依赖不变

```xml
<dependencies>
 <dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
```

在上面的基础上

#### 创建分组验证器

```java
public class Groups {

    public interface Update{}

    public interface Default{}
}

```

#### 创建insert和update两种类型

```java
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
```

#### 效果

![KUeAYR.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/KUeAYR.png)

