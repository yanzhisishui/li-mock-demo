package com.example.feignmockclient.strategy;

import com.example.feignmockclient.strategy.bo.RequestResolveResponse;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class AllRequestParamResolveStrategy extends AbstractFeignClientRequestResolveStrategy {
    @Override
    public boolean supports(RequestTemplate template) {
        Annotation[][] annotations = template.methodMetadata().method().getParameterAnnotations();
        for (Annotation[] annotation : annotations) {
            if (Arrays.stream(annotation).noneMatch(x -> x.annotationType().equals(RequestParam.class))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public RequestResolveResponse resolveRequestBody(RequestTemplate template) {
        Map<String, Object> requestBody = new HashMap<>();
        //查询参数
        Map<String, Collection<String>> queries = template.queries();
        queries.forEach((k, v) -> requestBody.put(k, v.stream().findFirst().orElse("")));

        RequestResolveResponse response = new RequestResolveResponse();
        response.setRequestBody(requestBody);
        response.setOriginUriList(originUri(template));
        return response;
    }
}
