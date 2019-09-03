package xyz.furur.load;

import org.springframework.boot.SpringApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

// 使用Feign，需要添加注解
@EnableFeignClients
@SpringCloudApplication
public class ServicesConsumerApplication {

    // 使用RestTemplate请取消注释
    // @Bean
    // @LoadBalanced
    // public RestTemplate restTemplate() {
    //     return new RestTemplate();
    // }

    public static void main(String[] args) {
        SpringApplication.run(ServicesConsumerApplication.class, args);
    }

}
