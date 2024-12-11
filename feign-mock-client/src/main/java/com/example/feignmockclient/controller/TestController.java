package com.example.feignmockclient.controller;

import com.example.feignmockclient.client.FeignMockServerClient;
import com.example.feignmockclient.client.request.RcsApplyRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    FeignMockServerClient client;
    @GetMapping("/test1")
    public Object test(){
        return client.rcsApplyQuery("applyNo");
    }

    @GetMapping("/test2")
    public Object test2(){
        RcsApplyRequest request = new RcsApplyRequest();
        request.setApplyNo("applyNo");
        request.setGateId("C017");
        return client.rcsApply(request);
    }
}
