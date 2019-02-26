package springcloud.client02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
@EnableDiscoveryClient
@EnableEurekaClient
@EnableHystrixDashboard
@EnableCircuitBreaker
public class Client02Application {

    public static void main(String[] args) {
        SpringApplication.run(Client02Application.class, args);
    }
}

/**
 * 当client向server注册时，它会提供一些元数据，例如主机和端口，URL，主页等。
 * Eureka server 从每个client实例接收心跳消息。
 * 如果心跳超时，则通常将该实例从注册server中删除。
 */


/**
 *
 * 链路追踪：Spring Cloud Sleuth+zipkin
 * Spring Cloud Sleuth 主要功能就是在分布式系统中提供追踪解决方案，并且兼容支持了 zipkin，你只需要在pom文件中引入相应的依赖即可。
 *
 * 微服务架构上通过业务来划分服务的，通过REST调用，对外暴露的一个接口，
 * 可能需要很多个服务协同才能完成这个接口功能，如果链路上任何一个服务出现问题或者网络超时，
 * 都会形成导致接口调用失败。随着业务的不断扩张，服务之间互相调用会越来越复杂。
 *
 * Span：基本工作单元，例如，在一个新建的span中发送一个RPC等同于发送一个回应请求给RPC，span通过一个64位ID唯一标识，trace以另一个64位ID表示，
 * span还有其他数据信息，比如摘要、时间戳事件、关键值注释(tags)、span的ID、以及进度ID(通常是IP地址)
 * span在不断的启动和停止，同时记录了时间信息，当你创建了一个span，你必须在未来的某个时刻停止它。
 * Trace：一系列spans组成的一个树状结构，例如，如果你正在跑一个分布式大数据工程，你可能需要创建一个trace。
 * Annotation：用来及时记录一个事件的存在，一些核心annotations用来定义一个请求的开始和结束
 * cs - Client Sent -客户端发起一个请求，这个annotion描述了这个span的开始
 * sr - Server Received -服务端获得请求并准备开始处理它，如果将其sr减去cs时间戳便可得到网络延迟
 * ss - Server Sent -注解表明请求处理的完成(当请求返回客户端)，如果ss减去sr时间戳便可得到服务端需要的处理请求时间
 * cr - Client Received -表明span的结束，客户端成功接收到服务端的回复，如果cr减去cs时间戳便可得到客户端从服务端获取回复的所有所需时间
 */


/**
 *
 * 只能用于ribbon
 * 断路器监控：Hystrix Dashboard，只能看单个服务，无实际用处，忽略
 *
 * 在微服务架构中为例保证程序的可用性，防止程序出错导致网络阻塞，出现了断路器模型。
 * 断路器的状况反应了一个程序的可用性和健壮性，它是一个重要指标。
 * Hystrix Dashboard是作为断路器状态的一个组件，提供了数据监控和友好的图形化界面。
 *
 * feign作为负载均衡时：
 * 加上@EnableHystrixDashboard注解，开启HystrixDashboard
 */

/**
 * 只能用于ribbon
 * Hystrix Turbin:
 * 看单个的Hystrix Dashboard的数据并没有什么多大的价值，要想看这个系统的Hystrix Dashboard数据就需要用到Hystrix Turbine。
 * Hystrix Turbine将每个服务Hystrix Dashboard数据进行了整合。Hystrix Turbine的使用非常简单，只需要引入相应的依赖和加上注解和配置就可以了。
 */

