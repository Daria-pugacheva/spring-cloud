package com.flamexander.cloud.hystrix;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@RestController
@EnableEurekaClient
@EnableCircuitBreaker
public class HystrixApplication {
    @Autowired
    private RestTemplate restTemplate;

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    public static void main(String[] args) {
        SpringApplication.run(HystrixApplication.class, args);
    }

    @HystrixCommand(fallbackMethod = "demoFallback")
    @GetMapping("/demo/client")
    public String demo() {
        // String result = restTemplate.getForObject("http://localhost:18221/abcr", String.class);
        String result = restTemplate.getForObject("http://eureka-client/abcr", String.class);
        return result;
    }

    public String demoFallback() {
        return "zero";
    }
}
