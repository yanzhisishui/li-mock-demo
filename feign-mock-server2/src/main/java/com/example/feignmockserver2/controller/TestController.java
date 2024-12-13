package com.example.feignmockserver2.controller;

import com.example.feignmockserver2.bo.RcsApplyRequest;
import com.example.feignmockserver2.bo.RcsApplyResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @PostMapping("/rcs/apply")
    public RcsApplyResponse test(@RequestBody RcsApplyRequest rcsApplyRequest) {
        RcsApplyResponse response = new RcsApplyResponse();
        response.setCode(200);
        response.setMessage("SUCCESS");
        return response;
    }
}
