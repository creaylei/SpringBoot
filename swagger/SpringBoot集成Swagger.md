# SpringBoot集成Swagger

> Swagger是动态接口文档，在前后端分离的项目中，接口文档越发显得重要，而swagger可以通过注解的方式，编写接口注释，顺便显示接口文档，还可以导出，方便很多。

## Swagger介绍

没有API文档工具之前，基本都是手写API文档的，如有在`Word`上写的，有在对应的项目目录下`readme.md`上写的，每个公司都有每个公司的玩法，无所谓好坏。但是这种手写文档带来的弊端就是维护起来苦不堪言，对于接口容易发生变化的开发者来说，维护文档就是噩梦…. 好在现如今市场上书写API文档的工具有很多，常见的有 [postman](https://www.getpostman.com/apps)、[yapi](https://github.com/YMFE/yapi)、[阿里的RAP](http://rapapi.org/org/index.do) 但是能称之为**框架**的，估计也只有`swagger`了。

> 优缺点

- 集成方便，功能强大
- 在线调试与文档生成
- 代码耦合，需要注解支持，但不影响程序性能

## 快速使用

1. 添加依赖

```xml
<dependency>
 <groupId>com.battcn</groupId>
 <artifactId>swagger-spring-boot-starter</artifactId>
 <version>1.4.5-RELEASE</version>
</dependency>
```

2. 配置属性

配置`spring.swagger.enabled`开启`swagger`的使用

```yml
spring:
  swagger:
    base-package: com.springboot         # 扫描包路径
    enabled: true
```

3. 实体类

```java
@ApiModel
public class User implements Serializable {

 private static final long serialVersionUID = 8655851615465363473L;

 private Long id;
 @ApiModelProperty("用户名")
 private String username;
 @ApiModelProperty("密码")
 private String password;

 // TODO  省略get set
}
```

4. Controller

```java
@RestController
@RequestMapping("/users")
@Api(tags = "1.1", description = "用户管理", value = "用户管理")
public class UserController {

 private static final Logger log = LoggerFactory.getLogger(UserController.class);

 @GetMapping
 @ApiOperation(value = "条件查询（DONE）")
 @ApiImplicitParams({
 @ApiImplicitParam(name = "username", value = "用户名", dataType = ApiDataType.STRING, paramType = ApiParamType.QUERY),
 @ApiImplicitParam(name = "password", value = "密码", dataType = ApiDataType.STRING, paramType = ApiParamType.QUERY),
 })
 public User query(String username, String password) {
 log.info("多个参数用  @ApiImplicitParams");
 return new User(1L, username, password);
 }

 @GetMapping("/{id}")
 @ApiOperation(value = "主键查询（DONE）")
 @ApiImplicitParams({
 @ApiImplicitParam(name = "id", value = "用户编号", dataType = ApiDataType.LONG, paramType = ApiParamType.PATH),
 })
 public User get(@PathVariable Long id) {
 log.info("单个参数用  @ApiImplicitParam");
 return new User(id, "u1", "p1");
 }

 @DeleteMapping("/{id}")
 @ApiOperation(value = "删除用户（DONE）")
 @ApiImplicitParam(name = "id", value = "用户编号", dataType = ApiDataType.LONG, paramType = ApiParamType.PATH)
 public void delete(@PathVariable Long id) {
 log.info("单个参数用 ApiImplicitParam");
 }

 @PostMapping
 @ApiOperation(value = "添加用户（DONE）")
 public User post(@RequestBody User user) {
 log.info("如果是 POST PUT 这种带 @RequestBody 的可以不用写 @ApiImplicitParam");
 return user;
 }

 @PutMapping("/{id}")
 @ApiOperation(value = "修改用户（DONE）")
 public void put(@PathVariable Long id, @RequestBody User user) {
 log.info("如果你不想写 @ApiImplicitParam 那么 swagger 也会使用默认的参数名作为描述信息 ");
 }
}
```

5. 主函数上配置`@EnableSwagger2Doc` 

```java
@EnableSwagger2Doc
@SpringBootApplication
public class Chapter10Application {

 public static void main(String[] args) {
 SpringApplication.run(Chapter10Application.class, args);
 }
}
```

## 效果

![KUpZtO.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/KUpZtO.png)

![KUpn9e.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/KUpn9e.png)