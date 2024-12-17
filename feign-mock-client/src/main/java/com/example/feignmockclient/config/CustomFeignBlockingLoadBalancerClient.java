package com.example.feignmockclient.config;

import com.alibaba.fastjson2.JSON;
import com.example.feignmockclient.consts.MockKeyConst;
import com.example.feignmockclient.entity.MockConfigItem;
import com.example.feignmockclient.service.MockConfigService;
import feign.Client;
import feign.Request;
import feign.Response;
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


    private final MockConfigService mockConfigService;

    public CustomFeignBlockingLoadBalancerClient(Client delegate, LoadBalancerClient loadBalancerClient, LoadBalancerClientFactory loadBalancerClientFactory,
                                                 List<LoadBalancerFeignRequestTransformer> transformers, MockConfigService mockConfigService) {
        super(delegate, loadBalancerClient, loadBalancerClientFactory, transformers);
        this.mockConfigService = mockConfigService;
    }

    /**
     * 自定义
     * */
    @Override
    public Response execute(Request request, Request.Options options) throws IOException {

        Map<String, Collection<String>> requestHeaders = request.headers();
        if(requestHeaders.containsKey(MockKeyConst.MATCH_MOCK_CONFIG_ITEM_ID)){
            //表示需要 mock
            Collection<String> headerValueList = requestHeaders.get(MockKeyConst.MATCH_MOCK_CONFIG_ITEM_ID);
            String mockItemId = headerValueList.stream().findFirst().orElseThrow();
            MockConfigItem configItem = mockConfigService.getMockConfigItem(Integer.parseInt(mockItemId));

            //返回 mock 的响应
            Map<String, Collection<String>> headers = new HashMap<>();
            headers.put("connection",List.of("keep-alive"));
            headers.put("content-type",List.of("application/json"));
            headers.put("keep-alive",List.of("timeout=60"));
            headers.put("transfer-encoding",List.of("chunked"));

            byte[] bytes = configItem.getMockResponse().getBytes();
            return Response.builder().status(HttpStatus.OK.value()).request(request)
                    .protocolVersion(Request.ProtocolVersion.HTTP_1_1)
                    .headers(headers)
                    .body(new ByteArrayInputStream(bytes),bytes.length)
                    .build();
        }
        return super.execute(request, options);
    }
}