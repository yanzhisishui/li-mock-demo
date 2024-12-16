package com.example.feignmockclient.config.interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.TypeReference;
import com.example.feignmockclient.entity.MockConfig;
import com.example.feignmockclient.entity.MockConfigItem;
import com.example.feignmockclient.mapper.MockConfigMapper;
import com.example.feignmockclient.service.MockConfigService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * Feign 调用的时候请求拦截器
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    private final MockConfigService mockConfigService;

    public FeignRequestInterceptor(MockConfigService mockConfigService) {
        this.mockConfigService = mockConfigService;
    }

    @Override
    public void apply(RequestTemplate template) {
        // 从header获取X-token


        String targetService = template.feignTarget().name(); //feign-mock-server
        String uri = template.path();                // /rcs/apply/query
        //serverName + uri 已经可以定义唯一接口

        MockConfig mockConfig = mockConfigService.getMockConfig(mockConfigService.CURRENT_SERVICE_NAME, targetService, uri);
        if (mockConfig == null) {
            //没有配置 mock 直接返回
            return;
        }
        List<MockConfigItem> mockConfigItemList = mockConfigService.queryOpenMockConfigItemList(mockConfig.getId());
        if (CollectionUtils.isEmpty(mockConfigItemList)) {
            //mock 配置项为空直接返回
            return;
        }
        if (mockConfigItemList.size() > 1) {
            log.info("FeignRequestInterceptor#apply open mockConfigItem size greater than 1,url-{}", targetService + uri);
        }

        /**
         * 方法请求体 method (Object arg0,Object arg1,Object arg...)
         * requestBody.arg0
         * requestBody.arg1
         * requestBody.arg...
         * */
        Map<String, Object> requestBody = new HashMap<>();

        //查询参数
        Map<String, Collection<String>> queries = template.queries();
        queries.forEach((k, v) -> requestBody.put(k, v.stream().findFirst().orElse("")));

        //请求参数
        Method method = template.methodMetadata().method();
        byte[] body = template.body();
        if (body != null) {
            Parameter[] parameters = method.getParameters();
            if (parameters.length > 1) {
                throw new RuntimeException("Feign Client 接口 RequestBody 参数个数不应该超过 1");
            }
            Map<String, Object> map = JSON.parseObject(body, JSONObject.class);
            String bodyParamName = parameters[0].getName();
            requestBody.put(bodyParamName, map);
        }

        // 用 EL 表达式解析条件
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("body", requestBody);

        for (MockConfigItem item : mockConfigItemList) {
            List<String> expressionList = JSON.parseObject(item.getExpressionListStr(), new TypeReference<>() {
            });
            //表达式列表必须全部匹配才行
            boolean allMatch = expressionList.stream().allMatch(expression -> {
                Boolean match;
                try {
                    match = parser.parseExpression(transfer(expression)).getValue(context, Boolean.class);
                } catch (Exception e) {
                    log.error("FeignRequestInterceptor#apply expression error url-{},expression-{}", targetService + uri, expression, e);
                    match = false;
                }
                return match;
            });
            if (allMatch) {
                //如果已经找到了匹配的 mock 配置，就可以添加mock标记，然后退出循环了
                template.header("x-mock-item-id", item.getId().toString());
                break;
            }
        }

    }


    /**
     * 将 body.arg0.x.x == 'x' 转换成 #body['arg0']['x']['x'] == 'x'
     */
    public static String transfer(String originExpression) {
        String[] first = originExpression.split("==");
        if (first.length > 2) {
            throw new RuntimeException("表达式错误");
        }
        String[] leftArr = first[0].trim().split("\\.");
        StringBuilder leftExpr = new StringBuilder();
        for (int i = 0; i < leftArr.length; i++) {
            if (i == 0) {
                leftExpr.append("#").append(leftArr[i]);
            } else {
                leftExpr.append("['").append(leftArr[i]).append("']");
            }
        }
        return leftExpr + " == " + first[1];
    }

}
