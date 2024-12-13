package com.example.feignmockclient.config;

import com.example.feignmockclient.interceptor.FeignRequestInterceptor;
import com.example.feignmockclient.mapper.MockConfigMapper;
import feign.Client;
import feign.RequestInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.loadbalancer.LoadBalancerFeignRequestTransformer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.List;

@Configuration
public class FeignConfiguration {

    @Autowired
    MockConfigMapper mockConfigMapper;
    /**
     * 自定义负载均衡调用客户端
     * */
   @Bean
   @Primary
   public Client feignBlockingLoadBalancerClient(
            LoadBalancerClient loadBalancerClient,
           LoadBalancerClientFactory loadBalancerClientFactory,
           List<LoadBalancerFeignRequestTransformer> transformers) {
       return new CustomFeignBlockingLoadBalancerClient(new Client.Default(null, null),
               loadBalancerClient,loadBalancerClientFactory, transformers,mockConfigMapper);
   }

   @Bean
    public RequestInterceptor feignRequestInterceptor(){
       return new FeignRequestInterceptor();
   }
}