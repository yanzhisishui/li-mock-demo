package com.example.feignmockclient.client;


import com.example.feignmockclient.client.request.RcsApplyRequest;
import com.example.feignmockclient.interceptor.FeignRequestInterceptor;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "feign-mock-server",configuration = FeignRequestInterceptor.class)
public interface FeignMockServerClient {
    @GetMapping("/rcs/apply/query")
    Object rcsApplyQuery(@RequestParam("applyNo") String applyNo);

    @PostMapping("/rcs/apply")
    Object rcsApply(@RequestBody RcsApplyRequest request);
}
