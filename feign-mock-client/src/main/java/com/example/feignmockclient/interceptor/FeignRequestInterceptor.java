package com.example.feignmockclient.interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Feign 调用的时候传token到下游
 */
//@Component
public class FeignRequestInterceptor implements RequestInterceptor {
    List<Class<?>> BASE_PARAM_TYPE_LIST = new ArrayList<>() {{
        add(String.class);
        add(Integer.class);
        add(Long.class);
        add(int.class);
        add(long.class);
    }};

    @Override
    public void apply(RequestTemplate template) {
        // 从header获取X-token


        String name = template.feignTarget().name(); //feign-mock-server
        String uri = template.path();                // /rcs/apply/query
        //serverName + uri 已经可以定义唯一接口

        //mock 匹配参数
        Map<String, String> map = new HashMap<>();
        map.put("gateId", "C017");
        map.put("applyNo", "applyNo");
        //请求参数
        Method method = template.methodMetadata().method();
        Class<?>[] parameterTypes = method.getParameterTypes();
         Class<?> parameterType = parameterTypes[0];
        byte[] body = template.body();
        String str  = new String(body);

        //原始参数
        JSONObject jsonObject = JSON.parseObject(str);

        if (BASE_PARAM_TYPE_LIST.contains(parameterType)) {
            System.out.println("基本类型");
        } else {
            //对象类型，判断 map 里面的是否全匹配 jsonObject
            boolean match = matchParam(map,jsonObject);
            template.request().header("x-mock-flag", "token");
            template.header("x-mock-flag", "token");
            Map<String, Collection<String>> headers = template.request().headers();
            System.out.println(match);
        }

    }

    /**
     * @param map 匹配规则，目标
     * @param jsonObject 原始参数
     * 判断 map 里面的是否全匹配 jsonObject
     * */

    private boolean matchParam(Map<String, String> map, JSONObject jsonObject) {
        for (String k : jsonObject.keySet()) {
            String originValue = jsonObject.getString(k);
            String targetValue = map.get(k);//获取原始参数的 key
            if(targetValue != null){
                if(!targetValue.equals(originValue)){
                    return false;
                }
            }
        }
       return true;
    }

}
