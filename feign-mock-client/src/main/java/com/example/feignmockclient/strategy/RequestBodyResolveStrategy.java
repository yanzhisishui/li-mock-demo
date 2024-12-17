package com.example.feignmockclient.strategy;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.example.feignmockclient.strategy.bo.RequestResolveResponse;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

@Component
public class RequestBodyResolveStrategy extends AbstractFeignClientRequestResolveStrategy {
    @Override
    public boolean supports(RequestTemplate template) {
        return template.body() != null;
    }

    /**
     * 方法请求体 method (Object arg0,Object arg1,Object
     * arg...)
     * requestBody.arg0
     * requestBody.arg1
     * requestBody.arg...
     */
    @Override
    public RequestResolveResponse resolveRequestBody(RequestTemplate template) {
        Map<String, Object> requestBody = new HashMap<>();
        Parameter[] parameters = template.methodMetadata().method().getParameters();
        if (parameters.length > 1) {
            throw new RuntimeException("Feign Client 接口 RequestBody 参数个数不应该超过 1");
        }
        byte[] body = template.body();
        Map<String, Object> map = JSON.parseObject(body, JSONObject.class);
        String bodyParamName = parameters[0].getName();
        requestBody.put(bodyParamName, map);
        RequestResolveResponse response = new RequestResolveResponse();
        response.setRequestBody(requestBody);
        response.setOriginUriList(originUri(template));
        return response;
    }
}
