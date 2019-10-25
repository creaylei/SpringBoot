# SpringBoot集成RabbitMq

## RabbitMQ安装

mac下使用 `brew install rabbitmq` 进行安装

![Kd8AV1.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/Kd8AV1.png)

看到这个页面就是安装成功了。

### 启动

进入到安装目录

> 1. cd usr/local/Cellar/rabbitmq/3.6.6
>
> 2. sbin/rabbitmq.sever

这里会遇到一个错误 

`ERROR: epmd error for host localhost: nxdomain (non-existing domain)`

解决的办法，将127.0.0.1 和 localhost绑定，使用switchhost或者 直接修改  etc/hosts/文件

![Kd8jLd.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/Kd8jLd.png)

然后执行下面命令，启动成功

![Kd8OQe.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/Kd8OQe.png)

此时，新开一个终端，进入到 `usr/local/Cellar/rabbitmq/3.7.14/sbin` 下，执行

> sudo ./rabbitmq-plugins enable rabbitmq_management

此时启动完成。

打开 [http://127.0.0.1:15672](http://127.0.0.1:15672/)   就可看到成功页面![KdG2kt.png](/Users/zhangleishuidihuzhu.com/Pictures/wiznote/KdG2kt.png)



## SpringBoot集成

### 引入pom依赖

```xml
<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-amqp</artifactId>
</dependency>
```

### 编写配置类

```java
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitConfig {

    public static final String DEFAULT_BOOK_QUEUE = "dev.book.register.default.queue";

    public static final String MANUAL_BOOK_QUEUE = "dev.book.register.manual.queue";

    @Bean
    public Queue defaultBookQueue() {
        return new Queue(DEFAULT_BOOK_QUEUE, true);
    }

    @Bean
    public Queue manualBookQueue() {
        return new Queue(MANUAL_BOOK_QUEUE, true);
    }
}
```

### 写一个消费者

```java
@Component
@Slf4j
public class BookHandler {

    @RabbitListener(queues = {RabbitConfig.DEFAULT_BOOK_QUEUE})
    public void listenAutoAck(String book, Message message, Channel channel) {
        final long deliverTag = message.getMessageProperties().getDeliveryTag();
        try {
            log.info("[listenerAutoAck 监听的消息] - [{}]", book);
            channel.basicAck(deliverTag, false);
        } catch (IOException e) {
            try {
                channel.basicRecover();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    @RabbitListener(queues = {RabbitConfig.MANUAL_BOOK_QUEUE})
    public void listenerManualAck(String book, Message message, Channel channel) {
        log.info("[listenerManualAck 监听的消息] - [{}]", book);
        try {
            // TODO 通知 MQ 消息已被成功消费,可以ACK了
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
        } catch (IOException e) {
            // TODO 如果报错了,那么我们可以进行容错处理,比如转移当前消息进入其它队列
        }
    }
}
```

### 效果

```java
2018-05-22 19:04:26.708  INFO 23752 --- [cTaskExecutor-1] com.battcn.handler.BookHandler           : [listenerAutoAck 监听的消息] - [com.battcn.entity.Book@77d8be18]
2018-05-22 19:04:26.709  INFO 23752 --- [cTaskExecutor-1] com.battcn.handler.BookHandler           : [listenerManualAck 监听的消息] - [com.battcn.entity.Book@8bb452]
```

