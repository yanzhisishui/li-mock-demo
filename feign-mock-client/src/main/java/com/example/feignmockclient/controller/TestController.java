package com.example.feignmockclient.controller;

import com.example.feignmockclient.client.FeignMockServerClient;
import com.example.feignmockclient.client.request.RcsApplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    FeignMockServerClient client;
    @GetMapping("/test1")
    public Object test(){
        return client.rcsApplyQuery("TQYJKN20240113191341383","291024578");
    }

    @GetMapping("/test2")
    public Object test2(){
        RcsApplyRequest request = new RcsApplyRequest();
        request.setApplyNo("applyNo");
        request.setGateId("C017");
        return client.rcsApply(request);
    }

    @GetMapping("/test3")
    public Object test3(){
        RcsApplyRequest request = new RcsApplyRequest();
        request.setApplyNo("applyNo");
        request.setGateId("C017");
        return client.rcsApplyQueryMap(request);
    }

    @GetMapping("/test4")
    public Object test4(){
        return client.rcsApplyQueryPathVariable("applyNo","C017");
    }
}
