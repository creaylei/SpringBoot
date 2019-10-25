# SpringBoot集成模板引擎Thymeleaf

> 常用的模板引擎有Thymeleaf、FreeMarker、Velocity等

在`SpringBoot` 使用上述模板，默认从`src/main/resources/templates` 下加载

## Thymeleaf简介

> [Thymeleaf官网](https://www.thymeleaf.org/)

`Thymeleaf`是现代化服务器端的Java模板引擎，不同与其它几种模板的是`Thymeleaf`的语法更加接近HTML，并且具有很高的扩展性。

**特点**

- 支持无网络环境下运行，由于它支持 html 原型，然后在 html 标签里增加额外的属性来达到模板+数据的展示方式。

  浏览器解释 html 时会忽略未定义的标签属性，所以 thymeleaf 的模板可以静态地运行；当有数据返回到页面时，Thymeleaf 标签会动态地替换掉静态内容，使页面动态显示。所以它可以让前端小姐姐在浏览器中查看页面的静态效果，又可以让程序员小哥哥在服务端查看带数据的动态页面效果。

- 开箱即用，为`Spring`提供方言，可直接套用模板实现`JSTL、 OGNL`表达式效果，避免每天因套用模板而修改`JSTL、 OGNL`标签的困扰。同时开发人员可以扩展自定义的方言。

- `SpringBoot`官方推荐模板，提供了可选集成模块(`spring-boot-starter-thymeleaf`)，可以快速的实现表单绑定、属性编辑器、国际化等功能。

## 使用

1. 引入pom依赖，在pom.xml中添加

```xml
<dependency>
 <groupId>org.springframework.boot</groupId>
 <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

2. 新建po类

```java
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class Author {

    private Integer age;

    private String name;

    private String email;
}
```

其中`@Data` 和 `@Accessors(chain = true)` 是lombok带的注解，`@Data` 是自动生成setter和getter方法，`@Accessors(chain = true)`是打开链式创建

3. 创建Controller

```java
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
```

这里有两种方法可用，第一种是直接返回`ModelAndView` 然后给视图设置属性和name，第二种是使用`Servlet` 带的`HttpServletRequest` ，对其设置属性。效果是一样的

4. 创建html模板文件

最后在 `src/main/resources/templates` 目录下创建一个名 `index.html` 的模板文件，可以看到 `thymeleaf` 是通过在标签中添加额外属性动态绑定数据的

```html
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml"
 xmlns:th="http://www.thymeleaf.org">
<head>
 <meta charset="UTF-8">
 <!-- 可以看到 thymeleaf 是通过在标签里添加额外属性来绑定动态数据的 -->
 <title th:text="${title}">Title</title>
 <!-- 在/resources/static/js目录下创建一个hello.js 用如下语法依赖即可-->
 <script type="text/javascript" th:src="@{/js/hello.js}"></script>
</head>
<body>
 <h1 th:text="${desc}">Hello World</h1>
 <h2>=====作者信息=====</h2>
 <p th:text="${author?.name}"></p>
 <p th:text="${author?.age}"></p>
 <p th:text="${author?.email}"></p>
</body>
</html>
```

## 展示

页面最终效果

![KNNuM4.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/KNNuM4.png)

