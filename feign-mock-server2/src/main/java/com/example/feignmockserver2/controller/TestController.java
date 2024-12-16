package com.example.feignmockserver2.controller;

import com.example.feignmockserver2.bo.RcsApplyRequest;
import com.example.feignmockserver2.bo.RcsApplyResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @PostMapping("/rcs/apply")
    public RcsApplyResponse test(@RequestBody RcsApplyRequest rcsApplyRequest) {
        RcsApplyResponse response = new RcsApplyResponse();
        response.setCode(200);
        response.setMessage("SUCCESS");
        return response;
    }

    @GetMapping("/test3")
    public Object test3() throws JsonProcessingException {
        // 初始化表达式解析器
        ExpressionParser parser = new SpelExpressionParser();
        // 初始化评估上下文
        StandardEvaluationContext context = new StandardEvaluationContext();

        // 假设我们有一个嵌套的JSON字符串
        String json = "{\"name\":\"John\",\"address\":{\"city\":1}}";
        // 将JSON字符串转换为Map对象
        Map<String, Object> jsonMap = new ObjectMapper().readValue(json, Map.class);

        // 将JSON映射到上下文
        context.setVariable("json", jsonMap);

        // 使用SPEL表达式取值
        Boolean city = parser.parseExpression("#json['address']['city'] == '1'").getValue(context, Boolean.class);
        System.out.println(city); // 输出: New York

        System.out.println("--------+:"+transfer("body.arg0.request.name == 'syc'"));
        return 1;

    }
    
    /**
     * 将 body.arg0.x.x == 'x' 转换成 #body['arg0']['x']['x'] == 'x'
     * */
    public static String transfer(String originExpression){
        String[] first = originExpression.split("==");
        if(first.length > 2){
            throw new RuntimeException("表达式错误");
        }
        String[] leftArr = first[0].trim().split("\\.");
        StringBuilder leftExpr = new StringBuilder();
        for (int i = 0; i < leftArr.length; i++) {
            if(i == 0){
                leftExpr.append("#").append(leftArr[i]);
            } else {
                leftExpr.append("['").append(leftArr[i]).append("']");
            }
        }
        return leftExpr + " == " +first[1];
    }
}
