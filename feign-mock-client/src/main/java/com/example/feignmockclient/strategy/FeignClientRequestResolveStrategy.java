package com.example.feignmockclient.strategy;

import com.example.feignmockclient.strategy.bo.RequestResolveResponse;
import feign.RequestTemplate;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * openfeign 的请求解析
 */
public interface FeignClientRequestResolveStrategy {
    boolean supports(RequestTemplate template);

    /**
     * 获取本次请求真实的 uri
     * */
    default List<String> originUri(RequestTemplate template) {
        Method method = template.methodMetadata().method();
        if (method.isAnnotationPresent(RequestMapping.class)) {
            RequestMapping annotation = method.getAnnotation(RequestMapping.class);
            return Arrays.stream(annotation.value()).collect(Collectors.toList());
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            PostMapping annotation = method.getAnnotation(PostMapping.class);
            return Arrays.stream(annotation.value()).collect(Collectors.toList());
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            PutMapping annotation = method.getAnnotation(PutMapping.class);
            return Arrays.stream(annotation.value()).collect(Collectors.toList());
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            DeleteMapping annotation = method.getAnnotation(DeleteMapping.class);
            return Arrays.stream(annotation.value()).collect(Collectors.toList());
        } else if (method.isAnnotationPresent(GetMapping.class)) {
            GetMapping annotation = method.getAnnotation(GetMapping.class);
            return Arrays.stream(annotation.value()).collect(Collectors.toList());
        }
        throw new RuntimeException("不存在的 FeignClient 请求");
    }

    ;

    RequestResolveResponse resolveRequestBody(RequestTemplate template);
}
