package com.example.feignmockclient.config;

import com.alibaba.fastjson2.JSON;
import feign.Client;
import feign.Request;
import feign.Response;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.loadbalancer.LoadBalancerFeignRequestTransformer;
import org.springframework.http.HttpStatus;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CustomFeignBlockingLoadBalancerClient extends FeignBlockingLoadBalancerClient {
    public CustomFeignBlockingLoadBalancerClient(Client delegate, LoadBalancerClient loadBalancerClient, LoadBalancerClientFactory loadBalancerClientFactory, List<LoadBalancerFeignRequestTransformer> transformers) {
        super(delegate, loadBalancerClient, loadBalancerClientFactory, transformers);
    }


    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        // 自定义实现 execute 方法
        Map<String, Collection<String>> headers = request.headers();
//        if(headers.containsKey("x-mock-flag")) {
//            //表示需要 Mock
//            Map<String,Object> result = new HashMap<>();
//            result.put("flag", headers.get("x-mock-flag"));
//            String jsonString = JSON.toJSONString(result);
//            return Response.builder().request(request).status(HttpStatus.OK.value()).body(jsonString.getBytes()).build();
//        }
        return super.execute(request, options);
    }
}