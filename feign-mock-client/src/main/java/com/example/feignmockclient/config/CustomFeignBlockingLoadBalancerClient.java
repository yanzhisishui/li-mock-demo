package com.example.feignmockclient.config;

import com.alibaba.fastjson2.JSON;
import com.example.feignmockclient.mapper.MockConfigMapper;
import feign.Client;
import feign.Request;
import feign.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.loadbalancer.support.LoadBalancerClientFactory;
import org.springframework.cloud.openfeign.loadbalancer.FeignBlockingLoadBalancerClient;
import org.springframework.cloud.openfeign.loadbalancer.LoadBalancerFeignRequestTransformer;
import org.springframework.http.HttpStatus;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CustomFeignBlockingLoadBalancerClient extends FeignBlockingLoadBalancerClient {


    private MockConfigMapper mockConfigMapper;

    public CustomFeignBlockingLoadBalancerClient(Client delegate, LoadBalancerClient loadBalancerClient, LoadBalancerClientFactory loadBalancerClientFactory,
                                                 List<LoadBalancerFeignRequestTransformer> transformers, MockConfigMapper mockConfigMapper) {
        super(delegate, loadBalancerClient, loadBalancerClientFactory, transformers);
        this.mockConfigMapper = mockConfigMapper;
    }

    /**
     * 自定义
     * */
    @Override
    public Response execute(Request request, Request.Options options) throws IOException {
        Map<String, Collection<String>> headers = new HashMap<>();
        headers.put("connection",List.of("keep-alive"));
        headers.put("content-type",List.of("application/json"));
        headers.put("keep-alive",List.of("timeout=60"));
        headers.put("transfer-encoding",List.of("chunked"));
        System.out.println(111);
        Map<String,Object> map = new HashMap<>();
        map.put("code",200);
        map.put("message","SUCCESS!");
        String jsonString = JSON.toJSONString(map);
        byte[] bytes = jsonString.getBytes();
        Response build = Response.builder().status(HttpStatus.OK.value()).request(request)
                .protocolVersion(Request.ProtocolVersion.HTTP_1_1)
                .headers(headers)
                .body(new ByteArrayInputStream(bytes),bytes.length)
                .build();
        if(true){
            return build;
        }
        return super.execute(request, options);
    }
}