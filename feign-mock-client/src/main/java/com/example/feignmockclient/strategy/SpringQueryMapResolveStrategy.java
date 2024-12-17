package com.example.feignmockclient.strategy;

import com.example.feignmockclient.strategy.bo.RequestResolveResponse;
import feign.RequestTemplate;
import org.springframework.cloud.openfeign.SpringQueryMap;
import org.springframework.stereotype.Component;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class SpringQueryMapResolveStrategy extends AbstractFeignClientRequestResolveStrategy {
    @Override
    public boolean supports(RequestTemplate template) {
        return hasSpringQueryMap(template.methodMetadata().method());
    }

    @Override
    public RequestResolveResponse resolveRequestBody(RequestTemplate template) {
        Map<String, Object> requestBody = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        //查询参数
        Map<String, Collection<String>> queries = template.queries();
        for (String key : queries.keySet()) {
            Collection<String> values = queries.get(key);
            map.put(key, values.stream().findFirst().orElse(""));
        }
        requestBody.put(template.methodMetadata().method().getParameters()[0].getName(), map);

        RequestResolveResponse response = new RequestResolveResponse();
        response.setRequestBody(requestBody);
        response.setOriginUriList(originUri(template));

        return response;
    }

    private boolean hasSpringQueryMap(Method method) {
        boolean hasSpringQueryMap = false;
        Annotation[][] parameterAnnotations = method.getParameterAnnotations();
        for (int i = 0; i < parameterAnnotations.length; i++) {
            Annotation[] annotations = parameterAnnotations[i];
            if (Arrays.stream(annotations).anyMatch(x -> x.annotationType().equals(SpringQueryMap.class))) {
                //被这玩意标注，也需要放入形参
                hasSpringQueryMap = true;
            }
        }
        return hasSpringQueryMap;
    }
}
