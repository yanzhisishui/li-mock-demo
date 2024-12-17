package com.example.feignmockclient.config.interceptor;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import com.example.feignmockclient.consts.MockKeyConst;
import com.example.feignmockclient.entity.MockConfig;
import com.example.feignmockclient.entity.MockConfigItem;
import com.example.feignmockclient.service.MockConfigService;
import com.example.feignmockclient.strategy.FeignClientRequestResolveStrategy;
import com.example.feignmockclient.strategy.bo.RequestResolveResponse;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Feign 调用的时候请求拦截器
 */
@Slf4j
public class FeignRequestInterceptor implements RequestInterceptor {
    private final MockConfigService mockConfigService;
    private final List<FeignClientRequestResolveStrategy> requestResolveStrategyList;

    public FeignRequestInterceptor(MockConfigService mockConfigService, List<FeignClientRequestResolveStrategy> requestResolveStrategyList) {
        this.mockConfigService = mockConfigService;
        this.requestResolveStrategyList = requestResolveStrategyList;
    }

    @Override
    public void apply(RequestTemplate template) {

        RequestResolveResponse response = requestResolveStrategyList.stream().filter(item -> item.supports(template))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("暂无策略"))
                .resolveRequestBody(template);

        String targetService = template.feignTarget().name(); //feign-mock-server
        List<String> uriList = response.getOriginUriList();   // /rcs/apply/query
        //serverName + uri 已经可以定义唯一接口

        //当前请求的 uri
        String uri = template.path();

        //为了提高性能，可以考虑把 mock 配置放到缓存中获取 todo
        MockConfig mockConfig = mockConfigService.getMockConfig(mockConfigService.CURRENT_SERVICE_NAME, targetService, uriList);
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
            log.warn("FeignRequestInterceptor#apply open mockConfigItem size greater than 1,url-{}", targetService + uri);
        }
        // 用 EL 表达式解析条件
        ExpressionParser parser = new SpelExpressionParser();
        StandardEvaluationContext context = new StandardEvaluationContext();
        context.setVariable("body", response.getRequestBody());

        for (MockConfigItem item : mockConfigItemList) {
            List<String> expressionList = JSON.parseObject(item.getExpressionListStr(), new TypeReference<>() {
            });
            //表达式列表必须全部匹配才行
            boolean allMatch = expressionList.stream().allMatch(expression -> {
                Boolean match;
                try {
                    match = parser.parseExpression(transferToSpel(expression)).getValue(context, Boolean.class);
                } catch (Exception e) {
                    log.error("FeignRequestInterceptor#apply expression error url-{},expression-{}", targetService + uri, expression, e);
                    match = false;
                }
                return match;
            });
            if (allMatch) {
                //如果已经找到了匹配的 mock 配置，就可以添加mock标记，然后退出循环了
                template.header(MockKeyConst.MATCH_MOCK_CONFIG_ITEM_ID, item.getId().toString());
                break;
            }
        }

    }

    /**
     * 将 body.arg0.x.x == 'x' 转换成 #body['arg0']['x']['x'] == 'x'
     */
    public static String transferToSpel(String originExpression) {
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
