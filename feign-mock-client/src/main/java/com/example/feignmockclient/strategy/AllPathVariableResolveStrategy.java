package com.example.feignmockclient.strategy;

import com.example.feignmockclient.strategy.bo.RequestResolveResponse;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.lang.annotation.Annotation;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class AllPathVariableResolveStrategy extends AbstractFeignClientRequestResolveStrategy {
    @Override
    public boolean supports(RequestTemplate template) {
        Annotation[][] annotations = template.methodMetadata().method().getParameterAnnotations();
        for (Annotation[] annotation : annotations) {
            if (Arrays.stream(annotation).noneMatch(x -> x.annotationType().equals(PathVariable.class))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public RequestResolveResponse resolveRequestBody(RequestTemplate template) {
        RequestResolveResponse response = new RequestResolveResponse();
        response.setOriginUriList(originUri(template));
        String uri = template.path();
        response.setRequestBody(parseUriToBody(response.getOriginUriList(), uri));
        return response;
    }

    /**
     * //原始路径 /rcs/apply/{applyNo}/{testParam}
     * //真实路径 /rcs/apply/TQYJKN20240113191341383/C017
     * 解析出 Map {applyNo=TQYJKN2024********1383, testParam=C017}
     */
    private static Map<String, Object> parseUriToBody(List<String> originUriList, String uri) {
        Map<String, Object> map = new HashMap<>();
        originUriList.forEach(originUri -> {
            String[] originArr = originUri.split("/");
            String[] actualArr = uri.split("/");
            for (int i = 0; i < originArr.length; i++) {
                if (originArr[i].equals(actualArr[i])) {
                    continue;
                }
                if (originArr[i].contains("{")) {
                    String key = originArr[i].replace("{", "").replace("}", "");
                    map.put(key, actualArr[i]);
                }
            }
        });
        return map;
    }
}
